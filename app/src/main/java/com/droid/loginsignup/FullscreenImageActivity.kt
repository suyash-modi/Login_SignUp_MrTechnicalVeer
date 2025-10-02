package com.droid.loginsignup

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jsibbold.zoomage.ZoomageView

class FullscreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_image)

        val imageView = findViewById<ZoomageView>(R.id.fullscreenImageView)
        val downloadButton = findViewById<Button>(R.id.downloadButton)

        val imageUrl = intent.getStringExtra("imageUrl")

        Glide.with(this).load(imageUrl).into(imageView)

        downloadButton.setOnClickListener {
            downloadImage(imageUrl)
        }
    }

    private fun downloadImage(url: String?) {
        if (url == null) return
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading Image")
            .setDescription("Downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "unsplash_image.jpg")
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }
}