package com.example.mfinance.presentation

import android.app.Application
import com.example.mfinance.data.AppContainer
import com.example.mfinance.data.AppDataContainer

class MobileFinanceApp : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}