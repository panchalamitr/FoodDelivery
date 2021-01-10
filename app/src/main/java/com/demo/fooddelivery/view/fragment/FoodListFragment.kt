package com.demo.fooddelivery.view.fragment

import Foods
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.fooddelivery.R
import com.demo.fooddelivery.adapter.FoodAdapter
import com.demo.fooddelivery.databinding.FragmentFoodListPageBinding
import com.demo.fooddelivery.model.Food
import timber.log.Timber
import kotlin.collections.ArrayList

class FoodListFragment : Fragment(R.layout.fragment_food_list_page) {


    private val foodAdapter = FoodAdapter()
    private lateinit var foodList: List<Food>
    private var filteredList = ArrayList<Food>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFoodListPageBinding.bind(view)

        val food = arguments?.getParcelable<Foods>("foodList")
        foodList = food?.item as List<Food>

        Timber.d("foodList: ${food.type}")
        binding.apply {
            chkBoxSpicy.setOnCheckedChangeListener { _, isChecked ->
                filteredList = if (isChecked) {
                    val spicyFoodList = foodList.filter { food ->
                        food.isSpicy
                    }
                    ArrayList(spicyFoodList)
                } else {
                    ArrayList(foodList)
                }
                foodAdapter.submitList(filteredList)
                foodAdapter.notifyDataSetChanged()
            }

            chkBoxVegan.setOnCheckedChangeListener { _, isChecked ->
                filteredList = if (isChecked) {
                    val veganFoodList = foodList.filter { food ->
                        food.isVegan
                    }
                    ArrayList(veganFoodList)
                } else {
                    ArrayList(foodList)
                }
                foodAdapter.submitList(filteredList)
                foodAdapter.notifyDataSetChanged()
            }

            foodAdapter.submitList(foodList)
            rvFoodList.adapter = foodAdapter
            rvFoodList.layoutManager = LinearLayoutManager(requireContext())
            rvFoodList.setHasFixedSize(true)
        }


    }



}