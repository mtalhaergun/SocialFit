package com.mte.fitnessapp.model.post

import java.io.Serializable

data class Post(val id:String,
                val userName:String,
                val imageUrl:String,
                val caption:String,
                val userId:String,
):Serializable
