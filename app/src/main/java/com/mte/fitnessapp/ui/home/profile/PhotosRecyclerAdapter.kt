package com.mte.fitnessapp.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.RecyclerPhotoLayoutBinding
import com.mte.fitnessapp.model.post.Post
import com.squareup.picasso.Picasso

class PhotosRecyclerAdapter(private var listPhotos : ArrayList<Post>) : RecyclerView.Adapter<PhotosRecyclerAdapter.PhotoVH>() {


    class PhotoVH(private val binding : RecyclerPhotoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerPhotoLayoutBinding.inflate(layoutInflater,parent,false)
        return PhotoVH(binding)
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        val image= holder.itemView.findViewById<ImageView>(R.id.photo)
        Picasso.get().load(listPhotos[position].imageUrl).into(image)
        holder.itemView.setOnClickListener {
            val navigation = ProfileFragmentDirections.actionProfileFragmentToPhotoDetailFragment(listPhotos[position])
            Navigation.findNavController(it).navigate(navigation)
        }

    }

    override fun getItemCount(): Int {
        return listPhotos.size
    }
}