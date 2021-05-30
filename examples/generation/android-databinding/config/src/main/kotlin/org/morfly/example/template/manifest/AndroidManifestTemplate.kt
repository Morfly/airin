package org.morfly.example.template.manifest


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