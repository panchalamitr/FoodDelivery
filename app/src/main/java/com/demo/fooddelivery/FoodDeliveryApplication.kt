package com.demo.fooddelivery

import androidx.multidex.MultiDexApplication
import com.airbnb.mvrx.Mavericks
import io.paperdb.Paper
import timber.log.Timber


class FoodDeliveryApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
        Timber.plant(Timber.DebugTree())
        Paper.init(this)
    }
}