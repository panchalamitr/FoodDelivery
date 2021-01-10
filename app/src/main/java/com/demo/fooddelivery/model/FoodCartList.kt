package com.demo.fooddelivery.model

import com.airbnb.mvrx.MavericksState


data class FoodCartList(val foodList: List<Food> = ArrayList<Food>()) : MavericksState