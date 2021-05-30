package com.morfly.airin.sample.lib3

import androidx.databinding.DataBindingUtil
import com.morfly.airin.sample.lib3.R
import com.morfly.airin.sample.lib3.databinding.LayoutLib3Binding
import android.app.Activity


object Lib3 {

    fun lib3() {
        println("lib3")
    }
    
    fun layout(activity: Activity) {
        val binding: LayoutLib3Binding = DataBindingUtil.setContentView(activity, R.layout.layout_lib3)
        val viewModel = Lib3ViewModel()
        binding.viewModel = viewModel
    }
}