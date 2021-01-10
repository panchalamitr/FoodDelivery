package com.demo.fooddelivery.repository

import OffersResponse
import com.demo.fooddelivery.model.ErrorFoods
import com.demo.fooddelivery.service.OffersApiService
import com.demo.fooddelivery.service.RetrofitInstance
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object OffersRepository {

    suspend fun getOffers(): NetworkResponse<OffersResponse, ErrorFoods> {
        lateinit var response: NetworkResponse<OffersResponse, ErrorFoods>
        withContext(Dispatchers.IO) {
            var foodItems =
                RetrofitInstance.buildService(OffersApiService::class.java)
            response = foodItems.getOffers()
        }
        return response
    }

}