@file:Suppress("FunctionName")

package org.morfly.airin.sample.template.src


fun binding_layout_template(
    vmVarName: String,
    vmPackageName: String,
    vmClassName: String,
    adapterName: String,
    vmPropertyName: String
    /**
     *
     */
) = """
<?xml version="1.0" encoding="utf-8"?>
<layout>
    <data>
        <variable
            name="$vmVarName"
            type="$vmPackageName.$vmClassName" />
    </data>

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:padding="16dp"
        android:orientation="vertical"
        android:gravity="center_vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:textSize="22dp"
            app:$adapterName="@{$vmVarName.$vmPropertyName}"
            android:textColor="@android:color/black"
            android:gravity="center_horizontal" />

    </LinearLayout>
</layout>
""".trimIndent()