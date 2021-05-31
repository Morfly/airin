@file:Suppress("FunctionName")

package org.morfly.airin.sample.template.src


fun binding_adapters_template(
    packageName: String,
    adapterName: String
) = """
package $packageName;

import androidx.databinding.BindingAdapter;
import android.widget.TextView;


public class BindingAdapters {

    @BindingAdapter("$adapterName")
    public static void $adapterName(TextView view, String $adapterName) {
        view.setText($adapterName);
    }
}

""".trimIndent()