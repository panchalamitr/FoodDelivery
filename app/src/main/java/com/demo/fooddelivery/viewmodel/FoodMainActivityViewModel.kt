package com.demo.fooddelivery.viewmodel

import Foods
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.fooddelivery.repository.FoodRepository
import com.demo.fooddelivery.repository.OffersRepository
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch
import timber.log.Timber

class FoodMainActivityViewModel : ViewModel() {

    private val foodsLiveData = MutableLiveData<List<Foods>>()
    private val offersLiveData = MutableLiveData<List<String>>()
    private val errorResponse = MutableLiveData<String>()

    fun getFoodList() {
        Timber.d("getFoodList")

        viewModelScope.launch {
            when (val result = FoodRepository.getFoods()) {
                is NetworkResponse.Success -> {
                    result?.let {
                        if (it.body.status == "Success") {
                            foodsLiveData.postValue(it.body?.foods)
                        } else {
                            errorResponse.postValue("No Foods found")
                        }
                    }
                }
                is NetworkResponse.ServerError -> {
                    result?.let {
                        errorResponse.postValue(it.body?.message)
                    }
                }
                /** When no internet connection **/
                is NetworkResponse.NetworkError -> {
                    result?.let {
                        errorResponse.postValue(it.error.message)
                    }
                }
            }
        }
    }

    fun getOffers() {
        viewModelScope.launch {
            when (val result = OffersRepository.getOffers()) {
                is NetworkResponse.Success -> {
                    if (result.body.status == "Success") {
                        offersLiveData.postValue(result.body?.offers)
                    } else {
                        errorResponse.postValue("No Foods found")
                    }
                }
                is NetworkResponse.ServerError -> {
                    result?.let {
                        errorResponse.postValue(it.body?.message)
                    }
                }
                /** When no internet connection **/
                is NetworkResponse.NetworkError -> {
                    result?.let {
                        errorResponse.postValue(it.error.message)
                    }
                }
            }
        }
    }

    fun getOffersResponse(): LiveData<List<String>> {
        return offersLiveData
    }

    fun getFoodsResponse(): LiveData<List<Foods>> {
        return foodsLiveData
    }

    fun getErrorResponse(): LiveData<String> {
        return errorResponse
    }
}