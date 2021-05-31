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

@file:Suppress("FunctionName")

package org.morfly.airin.sample.template.src


fun regular_file_template(
    packageName: String,
    bindingClassName: String,
    viewModelClassName: String,
    objectName: String,
    functionName: String,
    viewModelBindingPropertyName: String,
    layoutName: String
    /**
     *
     */
) = """
package $packageName

import androidx.databinding.DataBindingUtil
import $packageName.R
import $packageName.databinding.$bindingClassName
import android.app.Activity


object $objectName {

    fun $functionName() {
        println("$functionName")
    }
    
    fun layout(activity: Activity) {
        val binding: $bindingClassName = DataBindingUtil.setContentView(activity, R.layout.$layoutName)
        val viewModel = $viewModelClassName()
        binding.$viewModelBindingPropertyName = viewModel
    }
}
""".trimIndent()