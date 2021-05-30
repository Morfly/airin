@file:Suppress("FunctionName")

package org.morfly.example.template.src


fun view_model_template(
    vmPackageName: String,
    vmClassName: String,
    vmPropertyName: String,
    vmPropertyValue: String
    /**
     *
     */
) = """
package $vmPackageName


class $vmClassName {

    val $vmPropertyName: String
        get() = "$vmPropertyValue"
}
""".trimIndent()