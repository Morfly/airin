@file:Suppress("FunctionName")

package org.morfly.example.template.manifest


fun main_android_manifest_template(
    packageName: String,
    label: String,
    launcherActivity: String
    /**
     *
     */
) = """  
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="$packageName">

    <application
        android:allowBackup="true"
        android:label="$label"
        android:supportsRtl="true">
        <activity android:name="$launcherActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
""".trimIndent()