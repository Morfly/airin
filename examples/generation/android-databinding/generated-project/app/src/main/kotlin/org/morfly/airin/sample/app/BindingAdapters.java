package org.morfly.airin.sample.app;

import androidx.databinding.BindingAdapter;
import android.widget.TextView;


public class BindingAdapters {

    @BindingAdapter("text0")
    public static void text0(TextView view, String text0) {
        view.setText(text0);
    }
}
