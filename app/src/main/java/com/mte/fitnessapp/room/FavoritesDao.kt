package com.mte.fitnessapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mte.fitnessapp.model.favorites.Favorites

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    suspend fun getFavorites() : List<Favorites>

    @Insert
    suspend fun addFavorites(favorite : Favorites)

    @Delete
    suspend fun deleteFavorites(favorite: Favorites)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE name = :name LIMIT 1)")
    suspend fun searchName(name: String): Boolean
}