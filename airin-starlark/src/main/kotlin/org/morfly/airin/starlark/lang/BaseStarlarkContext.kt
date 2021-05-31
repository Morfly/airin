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

package org.morfly.airin.starlark.lang

import org.morfly.airin.starlark.lang.api.LanguageContext
import org.morfly.airin.starlark.lang.api.LanguageFeatureScope
import org.morfly.airin.starlark.lang.feature.*


/**
 * Starlark Language context that includes a base set of features that are applicable to all types of Starlark files
 * such as BUILD, WORKSPACE, .bzl and .star files.
 */
@LanguageFeatureScope
sealed class BaseStarlarkContext<C : LanguageContext> : LanguageContext(), StarlarkStatementsHolder,
    AssignmentsFeature, CustomNameAssignmentsFeature, BinaryPlusFeature,
    DynamicBinaryPlusFeature, CollectionsFeature, DynamicFunctionsFeature,
    EmptyLinesFeature, RawTextFeature, LoadStatementsFeature,
    ListComprehensionsFeature<C>, SlicesFeature, BinaryPercentsFeature
