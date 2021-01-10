package com.demo.fooddelivery.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.fooddelivery.R
import com.demo.fooddelivery.databinding.FoodItemBinding
import com.demo.fooddelivery.model.Food
import com.squareup.picasso.Picasso
import org.greenrobot.eventbus.EventBus


class FoodAdapter : ListAdapter<Food, FoodAdapter.FoodViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class FoodViewHolder(private val binding: FoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food) {
            binding.apply {
                tvFoodName.text = food.name
                tvFoodIngredients.text = food.ingredients
                tvFoodSize.text = food.size
                Picasso.get()
                    .load(food.url)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.loading_animation)
                    .into(ivFoodImage)
                btnAddFood.text = "${food.price} USD"
                btnAddFood.tag = food
                btnAddFood.setOnTouchListener { v, event ->
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN ->
                            btnAddFood.text = "Added 1"
                        MotionEvent.ACTION_UP -> {
                            val food = (v.tag as Food)
                            btnAddFood.text = "${food.price} USD"
                            EventBus.getDefault().post(food)
                        }
                    }
                    v?.onTouchEvent(event) ?: true
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Food)
        fun onCheckBoxClick(task: Food, isChecked: Boolean)
    }

    class DiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Food, newItem: Food) =
            oldItem == newItem
    }
}