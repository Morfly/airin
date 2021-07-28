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