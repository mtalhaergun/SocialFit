package com.mte.fitnessapp.model.post

data class Comment(val commentId:String,
                    val userId:String,
                   val postUserId:String,
                   val postId:String,
                   val comment:String,
                   val userName:String,
                   val control:Boolean)