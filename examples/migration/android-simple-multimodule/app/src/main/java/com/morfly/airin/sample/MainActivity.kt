package com.morfly.airin.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.morfly.airin.lib.Lib


class MainActivity : AppCompatActivity() {

    private lateinit var libInfoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        libInfoTextView = findViewById(R.id.lib_info_text)

        val lib = Lib()
        displayLibInfo(lib)
    }

    private fun displayLibInfo(lib: Lib) {
        val text = "This app uses '${lib.name}' library of version ${lib.version}."
        libInfoTextView.text = text
    }
}