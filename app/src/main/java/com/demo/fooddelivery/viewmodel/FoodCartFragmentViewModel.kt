package com.demo.fooddelivery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.fooddelivery.model.Food
import com.demo.fooddelivery.model.FoodCartList
import com.demo.fooddelivery.repository.FoodCartRepository
import io.paperdb.Paper
import kotlinx.coroutines.launch

class FoodCartFragmentViewModel(state: FoodCartList) : MvRxViewModel<FoodCartList>(state) {

    private val totalPriceLiveData = MutableLiveData<Int>()
    private val cartItemsLiveData = MutableLiveData<List<Food>>()
    private val errorResponse = MutableLiveData<String>()

    fun getCartItemList() {
        viewModelScope.launch {
            val foodCartList = FoodCartList(Paper.book().read<ArrayList<Food>>("Cart", ArrayList()))
            setState { copy(foodCartList.foodList) }
            //updateCartList(foodCartList)
        }
    }

    fun removeFoodFromCart(food: Food) {
        viewModelScope.launch {
            val foodCartList = FoodCartRepository.removeFoodFromCart(food)
            setState { copy(foodCartList.foodList) }
            //updateCartList(foodCartList)
        }
    }

    private fun updateCartList(foodCartList: ArrayList<Food>) {
        if (foodCartList.size == 0) {
            errorResponse.postValue("Cart is empty")
        } else {
            cartItemsLiveData.postValue(foodCartList)
        }
        totalPriceLiveData.postValue(FoodCartRepository.getTotalPrice())
    }

    fun observeTotalPrice() : LiveData<Int> {
        return totalPriceLiveData
    }

    fun observeCartItemListResponse(): LiveData<List<Food>> {
        return cartItemsLiveData
    }

    fun observeErrorResponse(): LiveData<String> {
        return errorResponse
    }
}