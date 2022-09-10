package com.elearning.rekamiacademy.util

import android.view.View

object ViewVisibiltyUtil {
    fun View.setVisible(){visibility = View.VISIBLE}
    fun View.setInvisible(){visibility = View.INVISIBLE}
    fun View.setGone(){visibility = View.GONE}
}