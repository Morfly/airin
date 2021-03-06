package org.morfly.airin.sample.app

import androidx.databinding.DataBindingUtil
import org.morfly.airin.sample.app.R
import org.morfly.airin.sample.app.databinding.LayoutAppBinding
import android.app.Activity


object App {

    fun app() {
        println("app")
    }
    
    fun layout(activity: Activity) {
        val binding: LayoutAppBinding = DataBindingUtil.setContentView(activity, R.layout.layout_app)
        val viewModel = AppViewModel()
        binding.viewModel = viewModel
    }
}