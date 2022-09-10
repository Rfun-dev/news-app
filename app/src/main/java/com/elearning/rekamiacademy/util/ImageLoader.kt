package com.elearning.rekamiacademy.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.elearning.rekamiacademy.R

object ImageLoader {
    fun ImageView.loadImage(context: Context,url : String?){
        val requestOption = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.no_photo_available)
            .error(R.color.black)
        Glide.with(context)
            .load(url)
            .override(700,700)
            .apply(requestOption)
            .into(this)
    }
}