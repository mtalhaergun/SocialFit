package com.mte.fitnessapp.ui.home.guide.questions

import com.mte.fitnessapp.base.BaseRepository
import com.mte.fitnessapp.network.ApiFactory
import javax.inject.Inject

class QuestionsRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getQuestions() = safeApiRequest {
        apiFactory.getQuestions()
    }
}