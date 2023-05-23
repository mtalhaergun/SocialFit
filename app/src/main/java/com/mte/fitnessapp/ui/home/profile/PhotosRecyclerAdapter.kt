package com.mte.fitnessapp.ui.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.mte.fitnessapp.databinding.RecyclerPhotoLayoutBinding

class PhotosRecyclerAdapter(private var listPhotos : List<String>) : RecyclerView.Adapter<PhotosRecyclerAdapter.PhotoVH>() {


    class PhotoVH(private val binding : RecyclerPhotoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerPhotoLayoutBinding.inflate(layoutInflater,parent,false)
        return PhotoVH(binding)
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        holder.itemView.setOnClickListener {
            val navigation = ProfileFragmentDirections.actionProfileFragmentToPhotoDetailFragment()
            Navigation.findNavController(it).navigate(navigation)
        }
    }

    override fun getItemCount(): Int {
        return listPhotos.size
    }
}