package com.demo.fooddelivery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.demo.fooddelivery.R
import com.demo.fooddelivery.view.activity.FoodMainActivity
import com.squareup.picasso.Picasso


class OffersViewPagerAdapter(foodMainActivity: FoodMainActivity, list: MutableList<String>) :
    RecyclerView.Adapter<OffersViewPagerAdapter.ViewHolder>() {

    private var mData: List<String> = list
    private var mInflater: LayoutInflater = LayoutInflater.from(foodMainActivity)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_viewpager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(mData[position])
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.loading_animation)
            .into(holder.ivOffers)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivOffers = itemView.findViewById<View>(R.id.ivOffers) as AppCompatImageView
    }

}