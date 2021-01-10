package com.demo.fooddelivery.service

import OffersResponse
import com.demo.fooddelivery.model.ErrorFoods
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

interface OffersApiService {

    @GET("offers.json?alt=media&token=7cb77082-d28e-4120-9e06-59562d6f6b0c")
    suspend fun getOffers(): NetworkResponse<OffersResponse, ErrorFoods>

}