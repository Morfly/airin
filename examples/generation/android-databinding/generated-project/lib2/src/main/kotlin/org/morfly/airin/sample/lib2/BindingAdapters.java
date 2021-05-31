package org.morfly.airin.sample.lib2;

import androidx.databinding.BindingAdapter;
import android.widget.TextView;


public class BindingAdapters {

    @BindingAdapter("text2")
    public static void text2(TextView view, String text2) {
        view.setText(text2);
    }
}
