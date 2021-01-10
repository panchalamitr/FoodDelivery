package com.demo.fooddelivery.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val id: Int = 0,
    val url: String = "",
    val name: String = "",
    val ingredients: String = "",
    val size: String = "",
    val price: Int = 0,
    val isVegan: Boolean = false,
    val isSpicy: Boolean = false
) : Parcelable
