package com.example.shkvarla.app

import android.app.Application
import com.example.shkvarla.di.databaseModule
import com.example.shkvarla.di.inventoryModule
import com.example.shkvarla.di.orderModule
import com.example.shkvarla.di.seviceModul
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(inventoryModule, orderModule, seviceModul, databaseModule))
        }
    }
}
