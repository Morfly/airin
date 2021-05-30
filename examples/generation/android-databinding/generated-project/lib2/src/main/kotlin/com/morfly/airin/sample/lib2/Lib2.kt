package com.morfly.airin.sample.lib2

import androidx.databinding.DataBindingUtil
import com.morfly.airin.sample.lib2.R
import com.morfly.airin.sample.lib2.databinding.LayoutLib2Binding
import android.app.Activity


object Lib2 {

    fun lib2() {
        println("lib2")
    }
    
    fun layout(activity: Activity) {
        val binding: LayoutLib2Binding = DataBindingUtil.setContentView(activity, R.layout.layout_lib2)
        val viewModel = Lib2ViewModel()
        binding.viewModel = viewModel
    }
}