package com.elearning.rekamiacademy.util

import android.widget.TextView
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setGone

object TextLoader {
    fun TextView.loadData(data : String){
        if(!data.isNullOrEmpty()) this.text = data else this.setGone()
    }
}