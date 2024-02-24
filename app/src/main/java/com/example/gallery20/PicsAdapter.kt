package com.example.gallery20

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.gallery20.databinding.PicRowBinding

class PicsAdapter(val context: Context,
    val myList: List<PicModel>) : RecyclerView.Adapter<PicsAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: PicRowBinding): ViewHolder(binding.root){
        val img = binding.image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = PicRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myList[position]
        Glide.with(context)
            .load(model.uri)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            openFullScreenActivity(model.uri)
        }
    }

    private fun openFullScreenActivity(imageUri: Uri) {
        val intent = Intent(context, FullscreenActivity::class.java)
        intent.putExtra(FullscreenActivity.EXTRA_IMAGE_URI, imageUri.toString())
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return myList.size
    }
}