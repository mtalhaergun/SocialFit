package com.mte.fitnessapp.ui.exercises.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mte.fitnessapp.model.favorites.Favorites
import com.mte.fitnessapp.room.FavoritesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository, private val fdao : FavoritesDao) : ViewModel() {

    val favorites : MutableLiveData<List<Favorites>> = MutableLiveData()

    fun getFavorites() = viewModelScope.launch {
        val result = repository.getFavorites()
        favorites.value = result
    }

    fun getFavoritesDao() : FavoritesDao{
        return fdao
    }


}