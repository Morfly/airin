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

package org.morfly.sample.feed

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.morfly.sample.AppComponentProvider
import org.morfly.sample.Navigator
import org.morfly.sample.R
import org.morfly.sample.ViewModelFactory
import org.morfly.sample.details.DetailsRoute
import org.morfly.sample.feed.di.DaggerFeedComponent
import org.morfly.sample.imagedata.Image
import javax.inject.Inject


class FeedFragment : Fragment(R.layout.fragment_feed) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<FeedViewModel>
    private val viewModel by viewModels<FeedViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerFeedComponent.builder()
            .appComponent((context.applicationContext as AppComponentProvider).appComponent)
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val feedRecyclerView = view.findViewById<RecyclerView>(R.id.feed_list)
        feedRecyclerView.layoutManager = GridLayoutManager(
            requireContext(), 3, GridLayoutManager.VERTICAL, false
        )
        val feedAdapter = FeedAdapter(onItemClick = ::navigateToDetails)
        val progressIndicator = view.findViewById<View>(R.id.progress)

        viewModel.viewState.observe(viewLifecycleOwner, {
            progressIndicator.visibility =
                if (it is FeedViewState.Loading) VISIBLE
                else GONE
            feedAdapter.submitList(it.images)
        })
        feedRecyclerView.adapter = feedAdapter
    }

    private fun navigateToDetails(image: Image) {
        (activity as? Navigator)?.navigateTo(DetailsRoute(image))
    }
}