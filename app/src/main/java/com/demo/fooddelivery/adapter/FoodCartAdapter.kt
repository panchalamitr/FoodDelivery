package com.demo.fooddelivery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.fooddelivery.R
import com.demo.fooddelivery.interfaces.OnFoodItemClickListener
import com.demo.fooddelivery.databinding.FoodCartItemBinding
import com.demo.fooddelivery.model.Food
import com.squareup.picasso.Picasso

class FoodCartAdapter(val onItemClickListener: OnFoodItemClickListener) : ListAdapter<Food, FoodCartAdapter.FoodViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FoodCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class FoodViewHolder(private val binding: FoodCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food) {
            binding.apply {
                tvFoodName.text = food.name
                tvFoodPrice.text = "${food.price} USD"
                Picasso.get()
                    .load(food.url)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.loading_animation)
                    .into(ivFoodImage)
                ivFoodRemove.tag = food
                ivFoodRemove.setOnClickListener {
                    val food = it.tag as Food
                    onItemClickListener.onItemClick(food)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Food, newItem: Food) =
            oldItem == newItem
    }
}