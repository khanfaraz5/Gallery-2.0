package com.example.gallery20

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.gallery20.databinding.ActivityCropBinding
import com.example.gallery20.databinding.ActivityFilterBinding

class Filter_Activity : AppCompatActivity() {
    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
    private lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        Glide.with(this)
            .load(Uri.parse(imageUri))
            .into(binding.filterActivityImageView)
    }
}