/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.morfly.airin.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.symbol.ClassKind.INTERFACE
import com.google.devtools.ksp.validate
import org.morfly.airin.*
import org.morfly.airin.starlark.lang.ListType
import org.morfly.airin.starlark.lang.api.Argument
import org.morfly.airin.starlark.lang.api.BracketsKind.Curly
import org.morfly.airin.starlark.lang.api.BracketsKind.Round
import org.morfly.airin.starlark.lang.api.FunctionKind
import org.morfly.airin.starlark.lang.api.FunctionKind.Expression
import org.morfly.airin.starlark.lang.api.LibraryFunction
import org.morfly.airin.starlark.lang.api.ReturnKind.Dynamic
import org.morfly.airin.starlark.lang.api.ReturnKind.Type
import org.morfly.airin.starlark.lang.api.Returns


private typealias FilePath = String

class LibraryGenerator(
    private val options: Map<String, String>,
    private val fileGenerator: FileGenerator,
    private val typeValidator: TypeValidator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var invoked = false

    private val libraryFiles = mutableMapOf<FilePath, GeneratedFile>()

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) return emptyList()

        val symbols = resolver.getSymbolsWithAnnotation(LibraryFunction::class.qualifiedName!!)

        symbols.filterIsInstance<KSClassDeclaration>()
            .forEach { it.accept(Visitor(), Unit) }

        for (file in libraryFiles.values) {
            validate(file)
            fileGenerator.generate(file)
        }

        invoked = true
        return emptyList()
    }

    private fun validate(file: GeneratedFile) {
        val functionNames = mutableSetOf<String>()

        for (func in file.functions) {
            if (func.shortName in functionNames) {
                val message = "Duplicate function declarations found with name '${func.shortName}'"
                logger.error(message, file.originalFile)
            }
            functionNames += func.shortName
        }
    }

    private inner class Visitor : KSVisitorVoid() {
        private val resolvedTypesCache = mutableMapOf<KSTypeReference, KSType>()

        private lateinit var functionKind: FunctionKind
        private val functionArguments = mutableListOf<Arg>()
        private var returnType: TypeDescriptor? = null
        private var varargArgument: Vararg? = null

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            if (classDeclaration.classKind != INTERFACE) {
                val message = "@${LibraryFunction::class.simpleName} must target only interfaces."
                logger.error(message, classDeclaration)
                return
            }

            val annotation = classDeclaration.annotations.first {
                it.shortName.asString() == LibraryFunction::class.simpleName
            }

            val arguments = annotation.arguments.toMap()

            val name = arguments.valueAs<String>(FUN_NAME)
            val scope = arguments.valueAs<List<KSType>>(FUN_SCOPE).map { it.toFunctionScope() }
            functionKind = arguments.valueAs<KSType>(FUN_KIND).toFunctionCallKind()
            val brackets = arguments.valueAsOrNull<List<KSType>>(FUN_BRACKETS)
                ?.mapTo(mutableSetOf()) { it.toBracketsKind() }
                ?: FUN_BRACKETS_DEFAULT

            classDeclaration.getAllProperties()
                .filter { it.validate() }
                .forEach { visitPropertyDeclaration(it, Unit) }

            val file = classDeclaration.containingFile!!
            val generatedFile = libraryFiles.getOrPut(file.filePath) {
                GeneratedFile(
                    shortName = file.fileName.removeSuffix(".kt"),
                    packageName = file.packageName.asString(),
                    originalFile = file
                )
            }

            if (functionKind == Expression) {
                if (returnType == null || returnType == VoidType) {
                    val message = "An 'expression' function must have non-void return type"
                    logger.error(message, classDeclaration)
                    return
                }
            }
            generatedFile.functions += GeneratedFunction(
                shortName = name,
                annotatedClassName = classDeclaration.simpleName.asString(),
                arguments = functionArguments,
                vararg = varargArgument,
                returnType = returnType ?: VoidType,
                scope = scope.toSet(),
                kind = functionKind,
                brackets
            )
        }

        override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
            val argAnnotation = property.annotations.firstOrNull {
                it.shortName.asString() == Argument::class.simpleName
            }
            val retAnnotation = property.annotations.firstOrNull {
                it.shortName.asString() == Returns::class.simpleName
            }
            if (functionKind == FunctionKind.Statement && retAnnotation != null) {
                val message = "A 'statement' function must not have specified return type"
                logger.error(message, property)
                return
            }
            if (argAnnotation != null && retAnnotation != null) {
                val arg = Argument::class.simpleName
                val ret = Returns::class.simpleName
                val message = "A property can not be annotated both with @$arg and @$ret."
                logger.error(message, property)
                return
            }

            if (retAnnotation != null)
                visitReturnProperty(property, retAnnotation)
            else visitArgumentProperty(property, argAnnotation)
        }

        private fun visitArgumentProperty(
            property: KSPropertyDeclaration, annotation: KSAnnotation?
        ) {
            val actualQualifiedName = property.type.findActualType()
                .getOrResolve().declaration
                .qualifiedName!!.asString()

            val arguments = annotation?.arguments?.toMap()
            val propertyName = property.simpleName.asString()
            val starlarkArgName = arguments?.valueAsOrNull<String>(ARG_UNDERLYING_NAME)
                ?.takeIf { it != Argument.NAME_DEFAULT }
                ?: propertyName
            val isRequired = arguments?.valueAsOrNull<Boolean>(ARG_REQUIRED) ?: ARG_REQUIRED_DEFAULT
            val vararg = arguments?.valueAsOrNull<Boolean>(ARG_VARARG) ?: ARG_VARARG_DEFAULT
            val isVararg = if (!vararg) vararg else {
                when (actualQualifiedName) {
                    ListType::class.qualifiedName -> {
                        if (varargArgument != null) {
                            val message = "Function must have not more than one vararg argument."
                            logger.error(message, property)
                            return
                        }
                        true
                    }
                    else -> {
                        val message = "Only arguments of list type must be used as vararg."
                        logger.error(message, property)
                        return
                    }
                }
            }

            if (isVararg) {
                val typeDescriptor = visitTypeReference(property.type)
                varargArgument = Vararg(
                    shortName = propertyName,
                    underlyingName = starlarkArgName,
                    type = typeDescriptor.genericArguments.first(),
                    fullType = typeDescriptor,
                    isRequired = isRequired
                )
            } else {
                functionArguments += Arg(
                    shortName = propertyName,
                    underlyingName = starlarkArgName,
                    type = visitTypeReference(property.type),
                    isRequired = isRequired
                )
            }
        }

        private fun visitReturnProperty(
            property: KSPropertyDeclaration, annotation: KSAnnotation
        ) {
            if (returnType != null) {
                val message = "A function must have return type specified not more than once."
                logger.error(message, property)
                return
            }

            val arguments = annotation.arguments.toMap()
            val kind = arguments.valueAsOrNull<KSType>(RET_KIND)?.toReturnKind() ?: RET_KIND_DEFAULT

            returnType = when (kind) {
                Type -> visitTypeReference(property.type)
                Dynamic -> DynamicType
            }
        }

        private fun visitTypeReference(typeRef: KSTypeReference, visitGenericArguments: Boolean = true): SpecifiedType {
            // validation
            val typeName = typeRef.findActualType().getOrResolve().declaration.qualifiedName!!.asString()
            if (!typeValidator.validate(typeName)) {
                val allowedTypes = typeValidator.allowedTypes.toDisplayableString()
                val message = "Invalid type $typeName. \nAllowed types are: \n$allowedTypes"
                logger.error(message, typeRef)
            }

            val genericArguments = mutableListOf<SpecifiedType>()
            if (visitGenericArguments) {
                val typeArguments = typeRef.element?.typeArguments ?: emptyList()
                for (arg in typeArguments) {
                    if (arg.variance == Variance.STAR) {
                        val message = "A function argument type must not have a '*' variance."
                        logger.error(message, arg)
                    }
                    arg.type?.let { ref ->
                        // validation
                        val typeArgName = ref.findActualType().getOrResolve().declaration.qualifiedName?.asString()
                        if (!typeValidator.validate(typeName, typeArgName)) {
                            val allowedTypes = typeValidator.allowedTypeArguments.toDisplayableString()
                            val message =
                                "Invalid generic type argument '$typeArgName'. \nAllowed types are: \n${allowedTypes}"
                            logger.error(message, ref)
                        }
                        genericArguments += visitTypeReference(ref, visitGenericArguments)
                    }
                }
            }

            val resolvedType = typeRef.getOrResolve()
            val typeDeclaration = resolvedType.declaration
            val actualType = if (typeDeclaration is KSTypeAlias) typeDeclaration.findActualType() else null
            return SpecifiedType(
                shortName = typeDeclaration.simpleName.asString(),
                qualifiedName = typeDeclaration.qualifiedName!!.asString(),
                packageName = typeDeclaration.packageName.asString(),
                isMarkedNullable = resolvedType.isMarkedNullable,
                actual = actualType?.let { visitTypeReference(it, visitGenericArguments = false) },
                genericArguments
            )
        }

        private fun KSTypeReference.getOrResolve(): KSType =
            resolvedTypesCache.getOrPut(this) { resolve() }

        private fun KSTypeAlias.findActualType(): KSTypeReference =
            when (val declaration = this.type.getOrResolve().declaration) {
                is KSTypeAlias -> declaration.findActualType()
                else -> this.type
            }

        private fun KSTypeReference.findActualType(): KSTypeReference =
            when (val declaration = this.getOrResolve().declaration) {
                is KSTypeAlias -> declaration.findActualType()
                else -> this
            }
    }

    companion object {
        const val FUN_NAME = "name"
        const val FUN_SCOPE = "scope"
        const val FUN_KIND = "kind"
        const val FUN_BRACKETS = "brackets"

        const val ARG_UNDERLYING_NAME = "underlyingName"
        const val ARG_REQUIRED = "required"
        const val ARG_VARARG = "vararg"

        const val RET_KIND = "kind"

        val FUN_BRACKETS_DEFAULT = setOf(Round, Curly) // FIXME remove when KSP is able to parse annotation def values.
        const val ARG_REQUIRED_DEFAULT = false // FIXME remove when KSP is able to parse annotation default values.
        const val ARG_VARARG_DEFAULT = false // FIXME remove when KSP is able to parse annotation default values.
        val RET_KIND_DEFAULT = Type // FIXME remove when KSP is able to parse annotation default values.
    }
}