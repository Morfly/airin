package com.morfly.airin.lib

import org.gradle.api.Project
import org.morfly.bazelgen.generator.file.BazelFile


//abstract class TemplateProvider<T> {
//
//    abstract fun provideTemplates(target: T): List<BazelFile>
//}
//
//abstract class ModuleTemplateProvider<T> : TemplateProvider<T>() {
//
//    abstract fun canProvide(target: T): Boolean
//
//    abstract override fun provideTemplates(target: T): List<BazelFile>
//}


//enum class ProviderType {
//    STANDALONE, REPEATED
//}
//
//abstract class TemplateProvider<T> internal constructor(val type: ProviderType) {
//
//    internal fun _canProvide(target: T) {
//        error("Not Implemented")
//    }
//
//
//    abstract fun provideTemplates(target: T): List<BazelFile>
//}
//
//abstract class StandaloneTemplateProvider<T> : TemplateProvider<T>(ProviderType.STANDALONE) {
//    private var provided = false
//
//
//}
//
//
//abstract class RepeatedTemplateProvider<T> : TemplateProvider<T>(ProviderType.REPEATED) {
//
//}
//
//internal interface MyIntf
//
//
//abstract class MyClass: MyIntf {
//
//}
//
//interface myIntf2: MyIntf {
//
//}
//
//
//internal interface StandaloneTemplateProvider<T> {
//    fun provideStandaloneTemplates(root: T): List<BazelFile>
//}
//
//internal interface RepeatedTemplateProvider<T> {
//    fun canProvide(module: T): List<BazelFile>
//    fun templateProviders(module: T): List<BazelFile>
//}
//
//interface CompositeTemplateProvider<T>:StandaloneTemplateProvider<T>,  {
//
//}

//internal enum class ProvisionType {
//    SINGLE_TIME, REPEATED
//}
//
//abstract class TemplateProvider<T> {
//
//    internal var provided = false
//
//    internal abstract val provisionType: ProvisionType
//
//    internal fun _canProvide(target: T): Boolean =
//        if (provisionType == SINGLE_TIME && provided) false
//        else canProvide(target)
//
//    abstract fun canProvide(target: T): Boolean
//
//    abstract fun allowNext(target: T): Boolean
//
//    abstract fun provideTemplates(target: T): List<BazelFile>
//}
//
//
//abstract class StandaloneTemplateProvider<T> : TemplateProvider<T>() {
//
//}
//
//abstract class ModuleTemplateProvider<T> : TemplateProvider<T>() {
//
//}


//abstract class TemplateProvider<T> {
//    internal var nextProvider: TemplateProvider<T>? = null
//
//    abstract fun provideTemplates(target: T): List<BazelFile>
//}


//////-----
//@Suppress("FunctionName")
//abstract class TemplateProvider<T> internal constructor() {
//    internal var active: Boolean = true
//
//    var nextProvider: TemplateProvider<T>? = null
//        internal set
//
//    internal open fun _canProvide(target: T): Boolean =
//        if (active) canProvide(target) else false
//
//    internal open fun _provideTemplates(target: T): List<BazelFile> =
//        if (active) provideTemplates(target)
//        else nextProvider?.provideTemplates(target) ?: emptyList()
//
//
//    open fun nextProvider(root: T): TemplateProvider<T>? = nextProvider
//
//    abstract fun canProvide(target: T): Boolean
//
//    abstract fun provideTemplates(target: T): List<BazelFile>
//}
//
//
//abstract class ModuleTemplateProvider<M> : TemplateProvider<M>() {
//
//    abstract override fun canProvide(module: M): Boolean
//
//    abstract override fun provideTemplates(module: M): List<BazelFile>
//}
//
//
//abstract class StandaloneTemplateProvider<T> : TemplateProvider<T>() {
//
//    override fun _provideTemplates(target: T): List<BazelFile> =
//        super._provideTemplates(target).also { active = false }
//}

//############


//sealed class Result {
//    data class Success(val templates: List<BazelFile>) : Result()
//
//    data class Failure(val message: String) : Result()
//}
//
//
//abstract class TemplateProvider<T> internal constructor() {
//    internal var active: Boolean = true
//
//    var nextProvider: TemplateProvider<T>? = null
//        internal set
//
//    internal open fun _nextProvider(): TemplateProvider<T>? {
//        if (nextProvider?.active == false) {
//            nextProvider = nextProvider?._nextProvider()
//        }
//        return nextProvider
//    }
//
//    internal open fun internalCanProvide(target: T): Boolean =
//        if (active) canProvide(target) else false
//
//    internal open fun internalProvideTemplates(target: T): Result =
//        if (internalCanProvide(target)) Result.Success(provideTemplates(target))
//        else nextProvider?.internalProvideTemplates(target)
//            ?: Result.Failure("")
//
//    abstract fun nextProvider(): TemplateProvider<T>?
//
//    abstract fun canProvide(target: T): Boolean
//
//    abstract fun provideTemplates(target: T): List<BazelFile>
//}

// --------------
//sealed class TemplateProvider<T> {
//
//    internal var active = true
//
//    var nextProvider: TemplateProvider<T>? = null
//
//    internal open fun internalProvideTemplates(target: T): List<BazelFile> =
//        if (active) provideTemplates(target)
//        else emptyList()
//
//    abstract fun provideTemplates(target: T): List<BazelFile>
//}
//
//abstract class StaticTemplateProvider<T> : TemplateProvider<T>() {
//
//    override fun internalProvideTemplates(target: T): List<BazelFile> {
//        val templates = mutableListOf<BazelFile>()
//        if (active) {
//            templates += provideTemplates(target)
//            active = false
//        }
//        nextProvider?.let {
//            templates += it.internalProvideTemplates(target)
//        }
//        return templates
//    }
//}
//
//abstract class DynamicTemplateProvider<T> : TemplateProvider<T>() {
//
//    abstract fun canProvide(target: T): Boolean
//}
//
//abstract class CompositeTemplateProvider<T> : TemplateProvider<T>() {
//
//
//    fun add(templateProvider: TemplateProvider<T>) {
//
//    }
//}

//########------#####

//sealed class TemplateProvider {
//
//    abstract val nextProvider: TemplateProvider
//}
//
//
//abstract class StaticTemplateProvider : TemplateProvider() {
//
//    abstract fun provideTemplates(root: Project): List<BazelFile>
//}
//
//
//abstract class DynamicTemplateProvider : TemplateProvider() {
//
//    abstract fun canProvide(target: Project): Boolean
//
//    abstract fun provideTemplates(relativePath: String, target: Project): List<BazelFile>
//}
//
//
//abstract class CompositeTemplateProvider : TemplateProvider() {
//
//    val children: List<TemplateProvider> = mutableListOf()
//
//    val thisProvider: TemplateProvider
//        get() {
//            if(children.isNotEmpty()) {
//                children[0]
//            }
//        }
//
//    abstract fun templateProviders(): List<Class<TemplateProvider>>
//}

abstract class State

abstract class TemplateProvider<S : State> {

    abstract fun nextProvider(type: String): TemplateProvider<S>?

    abstract fun canProvide(state: State): Boolean

    abstract fun allowNext(): Boolean

    abstract fun provideTemplates(relativePath: String, state: State): List<BazelFile>
}

abstract class StaticTemplateProvider<S : State> : TemplateProvider<S>() {

}

abstract class StandaloneTemplateProvider<S : State> : TemplateProvider<S>() {

}

abstract class RepeatedTemplateProvider<S : State> : TemplateProvider<S>() {

}















