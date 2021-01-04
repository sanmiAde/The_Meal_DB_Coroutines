package com.sanmidev.themealdbcoroutines.utils

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanmidev.themealdbcoroutines.R
import io.cabriole.decorator.ColumnProvider
import io.cabriole.decorator.GridMarginDecoration

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

