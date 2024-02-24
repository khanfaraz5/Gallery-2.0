package com.example.gallery20

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.gallery20.databinding.ActivityFullscreenBinding

class FullscreenActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }

    private lateinit var binding: ActivityFullscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        Glide.with(this)
            .load(Uri.parse(imageUri))
            .into(binding.fullScreenImageView)

        binding.cropBtn.setOnClickListener {
            val cropIntent = Intent(this,Crop_Activity::class.java)
            cropIntent.putExtra(Crop_Activity.EXTRA_IMAGE_URI, imageUri)
            startActivity(cropIntent)
        }

        binding.filterBtn.setOnClickListener {
            val filterIntent = Intent(this,Filter_Activity::class.java)
            filterIntent.putExtra(Filter_Activity.EXTRA_IMAGE_URI, imageUri)
            startActivity(filterIntent)
        }

        binding.closeButton.setOnClickListener {
            // Close the activity when the image is clicked
            finish()
        }
    }
}
