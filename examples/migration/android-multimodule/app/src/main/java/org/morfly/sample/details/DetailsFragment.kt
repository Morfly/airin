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