package com.droid.loginsignup.viewModels

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.droid.loginsignup.FullscreenImageActivity
import com.droid.loginsignup.R
import com.droid.loginsignup.models.UnsplashPhoto

class UnsplashAdapter : PagingDataAdapter<UnsplashPhoto, UnsplashViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return UnsplashViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnsplashViewHolder, position: Int) {
        val photo = getItem(position)
        photo?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean = oldItem.urls.regular == newItem.urls.regular
            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean = oldItem == newItem
        }
    }
}

class UnsplashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.imageView) 

    fun bind(photo: UnsplashPhoto) {
        Glide.with(itemView.context).load(photo.urls.regular).fitCenter().transform(RoundedCorners(30)).into(imageView)

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, FullscreenImageActivity::class.java)
            intent.putExtra("imageUrl", photo.urls.regular)
            itemView.context.startActivity(intent)
        }
    }
}
