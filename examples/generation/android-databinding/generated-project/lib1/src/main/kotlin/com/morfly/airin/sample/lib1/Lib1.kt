package com.morfly.airin.sample.lib1

import androidx.databinding.DataBindingUtil
import com.morfly.airin.sample.lib1.R
import com.morfly.airin.sample.lib1.databinding.LayoutLib1Binding
import android.app.Activity


object Lib1 {

    fun lib1() {
        println("lib1")
    }
    
    fun layout(activity: Activity) {
        val binding: LayoutLib1Binding = DataBindingUtil.setContentView(activity, R.layout.layout_lib1)
        val viewModel = Lib1ViewModel()
        binding.viewModel = viewModel
    }
}