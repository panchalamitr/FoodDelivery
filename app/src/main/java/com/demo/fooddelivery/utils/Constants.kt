package com.demo.fooddelivery.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val BASE_URL ="https://firebasestorage.googleapis.com/v0/b/media-share-821ef.appspot.com/o/"

    const val SG_LAT = 1.290270
    const val SG_LNG = 103.851959

    fun getCurrentTime(): String {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return df.format(Calendar.getInstance().time)
    }

}