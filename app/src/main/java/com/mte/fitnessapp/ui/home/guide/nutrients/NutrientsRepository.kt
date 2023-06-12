package com.mte.fitnessapp.ui.home.guide.nutrients

import com.mte.fitnessapp.base.BaseRepository
import com.mte.fitnessapp.network.ApiFactory
import com.mte.fitnessapp.utils.Constants.API_KEY
import javax.inject.Inject

class NutrientsRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getNutrients(query : String) = safeApiRequest {
        apiFactory.getNutritions(API_KEY,query)
    }
}