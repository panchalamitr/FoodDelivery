package com.demo.fooddelivery.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.demo.fooddelivery.R
import com.demo.fooddelivery.adapter.FoodCartAdapter
import com.demo.fooddelivery.databinding.FragmentCartPageBinding
import com.demo.fooddelivery.interfaces.OnFoodItemClickListener
import com.demo.fooddelivery.model.Food
import com.demo.fooddelivery.viewmodel.FoodCartFragmentViewModel
import kotlinx.android.synthetic.main.fragment_cart_page.*
import timber.log.Timber

class FoodCartListFragment :  Fragment(R.layout.fragment_cart_page),
    MavericksView, OnFoodItemClickListener {

    private lateinit var mTvTotalPrice: TextView
    private lateinit var mRvFoodCartList: RecyclerView
    private lateinit var foodAdapter: FoodCartAdapter
    private val foodCartViewModel: FoodCartFragmentViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCartPageBinding.bind(view)

        binding.apply {

            foodAdapter = FoodCartAdapter(this@FoodCartListFragment)
            mTvTotalPrice = tvTotalPrice
            mRvFoodCartList = rvFoodCartList
            foodAdapter.submitList(ArrayList<Food>())
            mRvFoodCartList.adapter = foodAdapter

            mRvFoodCartList.layoutManager = LinearLayoutManager(requireContext())
            mRvFoodCartList.setHasFixedSize(true)
        }

        foodCartViewModel.getCartItemList()

//        observeFoodCartListItems()
        observeErrorResponse()
        observeTotalPrice()
    }


    private fun observeFoodCartListItems(){
        foodCartViewModel.observeCartItemListResponse().observe(viewLifecycleOwner, { foods ->
            foodAdapter.submitList(foods)
            foodAdapter.notifyDataSetChanged()
            mRvFoodCartList.invalidate()
        })
    }


    private fun observeErrorResponse() {
        foodCartViewModel.observeErrorResponse().observe(viewLifecycleOwner, { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        })
    }

    private fun observeTotalPrice() {
        foodCartViewModel.observeTotalPrice().observe(viewLifecycleOwner, { total ->
            mTvTotalPrice.text = "$total USD"
        })
    }

    override fun onItemClick(food: Food) {
        foodCartViewModel.removeFoodFromCart(food)
    }

    override fun invalidate() = withState(foodCartViewModel) { foods ->
        Timber.d("Invalidate Called with foods : ${foods.foodList.size}")
        //foodCartViewModel.observeCartItemListResponse().observe(viewLifecycleOwner, { foods ->
        foodAdapter.submitList(foods.foodList)

        //})
    }

}