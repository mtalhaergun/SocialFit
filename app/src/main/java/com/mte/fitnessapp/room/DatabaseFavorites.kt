package com.mte.fitnessapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mte.fitnessapp.model.favorites.Favorites

@Database(entities = [Favorites::class], version = 1)
abstract class DatabaseFavorites : RoomDatabase() {
    abstract fun getFavoritesDao() : FavoritesDao
}