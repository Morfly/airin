@file:Suppress("FunctionName")

package org.morfly.airin.sample.template.manifest


fun android_manifest_template(
    packageName: String
    /**
     *
     */
) = """
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="$packageName">
</manifest>    
""".trimIndent()