package com.mte.fitnessapp.ui.exercises.favorites

import com.mte.fitnessapp.model.favorites.Favorites
import com.mte.fitnessapp.room.FavoritesDao
import javax.inject.Inject

class FavoritesRepository @Inject constructor (var fdao : FavoritesDao) {

    suspend fun getFavorites() : List<Favorites>{
        return fdao.getFavorites()
    }


}