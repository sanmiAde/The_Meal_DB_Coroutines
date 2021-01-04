package com.sanmidev.themealdbcoroutines.utils

import android.widget.ImageView
import coil.load

object CoilImageLoader : ImageLoader {

    //TODO [0A_4-01-2021] Check if coil supports caching.
    override fun loadImage(view: ImageView, imageUrl: String,) {
        view.load(imageUrl) {
            crossfade(true)
        }
    }
}