package com.demo.fooddelivery.interfaces

import com.demo.fooddelivery.model.Food

interface OnFoodItemClickListener {
    fun onItemClick(task: Food)
}