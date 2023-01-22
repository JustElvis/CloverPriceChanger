package com.example.shkvarla.di

import com.clover.sdk.util.CloverAccount
import com.clover.sdk.v3.inventory.InventoryConnector
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val inventoryModule = module {
    single {
        InventoryConnector(androidContext(),
            CloverAccount.getAccount(androidContext()), null).apply {
            connect()
        }
    }
}
