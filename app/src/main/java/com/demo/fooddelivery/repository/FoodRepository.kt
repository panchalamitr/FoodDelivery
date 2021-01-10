package com.demo.fooddelivery.repository

import FoodResponse
import com.demo.fooddelivery.model.ErrorFoods
import com.demo.fooddelivery.service.FoodApiService
import com.demo.fooddelivery.service.RetrofitInstance
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FoodRepository {


    suspend fun getFoods(): NetworkResponse<FoodResponse, ErrorFoods> {
        lateinit var response: NetworkResponse<FoodResponse, ErrorFoods>
        withContext(Dispatchers.IO) {
            var foodItems =
                RetrofitInstance.buildService(FoodApiService::class.java)
            response = foodItems.getFoodItems()
        }
        return response
    }

}