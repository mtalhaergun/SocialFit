package com.mte.fitnessapp.ui.exercises.favorites.adapters

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mte.fitnessapp.databinding.RecyclerFavoritesLayoutBinding
import com.mte.fitnessapp.model.favorites.Favorites
import com.mte.fitnessapp.room.FavoritesDao
import com.mte.fitnessapp.ui.exercises.favorites.FavoritesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesRecyclerAdapter(private val viewModel: FavoritesViewModel) : RecyclerView.Adapter<FavoritesRecyclerAdapter.FavoritesVH>() {

    var favoriteList = emptyList<Favorites>()
    var oldPosition = -1
    var fdao : FavoritesDao? = null

    class FavoritesVH(private val binding : RecyclerFavoritesLayoutBinding, private val viewModel: FavoritesViewModel) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite : Favorites,position: Int,fdao : FavoritesDao){
            binding.favorite = favorite
            binding.executePendingBindings()

            binding.likeButton.setOnClickListener {
                val job = CoroutineScope(Dispatchers.Main).launch {
                    fdao.deleteFavorites(favorite)
                    viewModel.favorites.value = fdao.getFavorites()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerFavoritesLayoutBinding.inflate(layoutInflater,parent,false)
        return FavoritesVH(binding,viewModel)
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        holder.bind(favoriteList[position],position,fdao!!)

    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    fun setFavorites(list : List<Favorites>){
        favoriteList = list
        notifyDataSetChanged()
    }


    fun getDao(favoritesDao : FavoritesDao){
        fdao = favoritesDao
    }
}
