package com.demo.fooddelivery.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.andremion.counterfab.CounterFab
import com.demo.fooddelivery.R
import com.demo.fooddelivery.view.fragment.FoodCartListFragment
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*


class FoodCartListActivity : AppCompatActivity() {

    lateinit var fabCreditCard: CounterFab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)

        val vpCartList = findViewById<ViewPager>(R.id.vpCartList)
        val vpCartType = findViewById<SmartTabLayout>(R.id.vpCartType)
        fabCreditCard = findViewById<CounterFab>(R.id.fabCreditCard)

        val fragmentPagerItemAdapter = FragmentPagerItemAdapter(
            supportFragmentManager,
            FragmentPagerItems.with(this@FoodCartListActivity)
                .add("Cart", FoodCartListFragment::class.java)
                .add("Orders", FoodCartListFragment::class.java)
                .add("Information", FoodCartListFragment::class.java)
                .create()
        )

        vpCartList.adapter = fragmentPagerItemAdapter
        vpCartType.setViewPager(vpCartList)
    }


}