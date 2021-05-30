package com.morfly.airin.sample.app

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.morfly.airin.sample.app.R
import com.morfly.airin.sample.app.databinding.LayoutAppBinding


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: LayoutAppBinding = DataBindingUtil.setContentView(this, R.layout.layout_app)
        val viewModel = AppViewModel()
        binding.viewModel = viewModel
    }
}