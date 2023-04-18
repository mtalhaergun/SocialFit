package com.mte.fitnessapp.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mte.fitnessapp.utils.Extensions.loadImage

class ImageDetailBinding {
    companion object{

        @BindingAdapter("load_image_detail")
        @JvmStatic
        fun loadImage(imageView: ImageView, url : String){
            val imageUrl = url
            imageView.loadImage(imageUrl)
        }

    }
}