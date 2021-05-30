package com.morfly.airin.sample.lib3;

import androidx.databinding.BindingAdapter;
import android.widget.TextView;


public class BindingAdapters {

    @BindingAdapter("text3")
    public static void text3(TextView view, String text3) {
        view.setText(text3);
    }
}
