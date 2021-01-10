package com.demo.fooddelivery.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.andremion.counterfab.CounterFab
import com.demo.fooddelivery.R
import com.demo.fooddelivery.adapter.OffersViewPagerAdapter
import com.demo.fooddelivery.model.Food
import com.demo.fooddelivery.view.fragment.FoodListFragment
import com.demo.fooddelivery.viewmodel.FoodMainActivityViewModel
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList


class FoodMainActivity : AppCompatActivity() {
    private lateinit var foodMainActivityViewModel : FoodMainActivityViewModel
    var foodItems = ArrayList<Food>()
    lateinit var fabCart: CounterFab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabCart = findViewById<CounterFab>(R.id.fabCart)
        fabCart.setOnClickListener {
            startActivity(Intent(MainActivity@ this, FoodCartListActivity::class.java))
        }


        foodMainActivityViewModel = ViewModelProvider(this).get(FoodMainActivityViewModel::class.java)
        foodMainActivityViewModel.getFoodList()
        foodMainActivityViewModel.getOffers()

        observeOffers()
        observeFoodList()
        observeErrorResponse()
    }

    private fun observeOffers() {

        foodMainActivityViewModel.getOffersResponse().observe(FoodMainActivity@this, { offers ->
            val vpOffers = findViewById<ViewPager2>(R.id.viewPager2)

            vpOffers.adapter = OffersViewPagerAdapter(MainActivity@ this,
                offers as MutableList<String>
            )

            var currentPage = 0
            val handler = Handler()
            val update = Runnable {
                if (currentPage == offers.size) {
                    currentPage = 0
                }

                //The second parameter ensures smooth scrolling
                vpOffers.setCurrentItem(currentPage++, true)
            }

            Timer().schedule(object : TimerTask() {
                // task to be scheduled
                override fun run() {
                    handler.post(update)
                }
            }, 1000, 1000)

        })
    }

    private fun observeFoodList(){
        val viewpagerFoodList = findViewById<ViewPager>(R.id.vpFoodList)
        val viewPagerTab = findViewById<SmartTabLayout>(R.id.vpFoodTypeList)

        foodMainActivityViewModel.getFoodsResponse().observe(FoodMainActivity@ this, { foodList ->
            val bundle = Bundle()
            bundle.putParcelable("foodList", foodList[0])
            val viewPager = FragmentPagerItems.with(this@FoodMainActivity)
                .add(foodList[0].type, FoodListFragment::class.java, bundle)
                .create()

            val fragmentPagerItemAdapter = FragmentPagerItemAdapter(
                supportFragmentManager,
                viewPager
            )

            viewpagerFoodList.adapter = fragmentPagerItemAdapter
            viewPagerTab.setViewPager(viewpagerFoodList)


            for (i in 1 until foodList.size) {
                val bundle = Bundle()
                bundle.putParcelable("foodList", foodList[i])
                viewPager.add(FragmentPagerItem.of(foodList[i].type, FoodListFragment::class.java, bundle))
                fragmentPagerItemAdapter.notifyDataSetChanged();
                viewPagerTab.setViewPager(viewpagerFoodList);
            }
        })
    }

    private fun observeErrorResponse() {
        foodMainActivityViewModel.getErrorResponse().observe(FoodListFragment@ this, {
            Toast.makeText(MainActivity@this, it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        foodItems = Paper.book().read("Cart", ArrayList())
        fabCart.count = foodItems.size
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(food: Food) { /* Do something */
        fabCart.increase()
        foodItems.add(food)
        Paper.book().write("Cart", foodItems)
    }
}