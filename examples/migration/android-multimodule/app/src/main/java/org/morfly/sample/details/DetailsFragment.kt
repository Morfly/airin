/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.morfly.sample.details

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import org.morfly.sample.AppComponentProvider
import org.morfly.sample.R
import org.morfly.sample.ViewModelFactory
import org.morfly.sample.details.di.DaggerDetailsComponent
import org.morfly.sample.imagedata.Image
import javax.inject.Inject


class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DetailsViewModel>
    private val viewModel by viewModels<DetailsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val args = requireArguments()
        val image = Image(id = args.getLong(ARG_IMAGE_ID), url = args.getString(ARG_IMAGE_URL)!!)
        DaggerDetailsComponent.factory().create(
            appComponent = (context.applicationContext as AppComponentProvider).appComponent,
            image = image
        ).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageView = view.findViewById<ImageView>(R.id.img)

        when (val viewState = viewModel.viewState.value) {
            is DetailsViewState.Image -> {
                imageView.load(viewState.url)
            }
        }
    }

    companion object {
        const val ARG_IMAGE_ID = "IMAGE_ID"
        const val ARG_IMAGE_URL = "IMAGE_URL"
    }
}