package com.demo.fooddelivery.repository

import com.demo.fooddelivery.model.Food
import com.demo.fooddelivery.model.FoodCartList
import io.paperdb.Paper

object FoodCartRepository {

    fun getFoodCartList(): FoodCartList {
        return FoodCartList(Paper.book().read<ArrayList<Food>>("Cart", ArrayList()))
    }

    fun removeFoodFromCart(food: Food): FoodCartList {
        val foodCartList = getFoodCartList()
        val foodList = ArrayList(foodCartList.foodList)
        foodList.remove(food)
        Paper.book().write("Cart", foodList)
        return FoodCartList(foodList)
    }

    fun getTotalPrice(): Int {
        val foodCartList = getFoodCartList()
        var total = 0
        for (foodCart in foodCartList.foodList) {
            total += foodCart.price
        }
        return total
    }
}