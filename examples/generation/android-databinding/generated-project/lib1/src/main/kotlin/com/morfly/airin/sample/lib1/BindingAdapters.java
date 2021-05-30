package com.morfly.airin.sample.lib1;

import androidx.databinding.BindingAdapter;
import android.widget.TextView;


public class BindingAdapters {

    @BindingAdapter("text1")
    public static void text1(TextView view, String text1) {
        view.setText(text1);
    }
}
