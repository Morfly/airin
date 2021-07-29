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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.morfly.sample.R
import org.morfly.sample.imagedata.Image


class FeedAdapter(val onItemClick: (image: Image) -> Unit) :
    RecyclerView.Adapter<FeedAdapter.ImageViewHolder>() {

    private val differ = AsyncListDiffer(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.load(image = differ.currentList[position])
    }

    override fun getItemCount(): Int =
        differ.currentList.size

    fun submitList(list: List<Image>) {
        differ.submitList(list)
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.img)
        private var image: Image? = null

        init {
            imageView.setOnClickListener {
                image?.let { onItemClick(it) }
            }
        }

        fun load(image: Image) {
            this.image = image
            imageView.load(image.url) {
                crossfade(true)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Image>() {

        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.url == newItem.url
    }
}