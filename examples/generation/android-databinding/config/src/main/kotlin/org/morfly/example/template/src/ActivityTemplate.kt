@file:Suppress("FunctionName")

package org.morfly.example.template.src


fun activity_template(
    packageName: String,
    className: String,
    bindingClassName: String,
    layoutName: String,
    viewModelClassName: String,
    viewModelBindingPropertyName: String
    /**
     *
     */
) = """
package $packageName

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import $packageName.R
import $packageName.databinding.$bindingClassName


class $className : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: $bindingClassName = DataBindingUtil.setContentView(this, R.layout.$layoutName)
        val viewModel = $viewModelClassName()
        binding.$viewModelBindingPropertyName = viewModel
    }
}
""".trimIndent()