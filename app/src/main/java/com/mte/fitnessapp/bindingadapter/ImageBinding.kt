package com.mte.fitnessapp.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mte.fitnessapp.utils.Extensions.loadImage

class ImageBinding {
    companion object{

        @BindingAdapter("load_image")
        @JvmStatic
        fun loadImage(imageView: ImageView, videoId : String){
            val imageUrl = "https://img.youtube.com/vi/${videoId}/maxresdefault.jpg"
            imageView.loadImage(imageUrl)
        }
    }
}