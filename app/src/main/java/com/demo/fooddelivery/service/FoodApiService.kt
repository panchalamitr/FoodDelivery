package com.demo.fooddelivery.service

import FoodResponse
import com.demo.fooddelivery.model.ErrorFoods
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

interface FoodApiService {

    @GET("foods.json?alt=media&token=1fccea90-c64c-47d8-8a56-3ee9c651bd17")
    suspend fun getFoodItems():
            NetworkResponse<FoodResponse, ErrorFoods>

}