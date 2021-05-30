@file:Suppress("FunctionName")

package org.morfly.example.template.src


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