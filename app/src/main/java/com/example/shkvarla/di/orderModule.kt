package com.example.shkvarla.di

import com.clover.sdk.util.CloverAccount
import com.clover.sdk.v3.order.OrderConnector
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val orderModule = module {
    single {
        OrderConnector(androidContext(), CloverAccount.getAccount(androidContext()), null)
    }
}
