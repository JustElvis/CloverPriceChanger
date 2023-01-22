package com.example.shkvarla.di

import com.example.shkvarla.service.ItemService
import org.koin.dsl.module

val serviceModule = module {
    single {
        ItemService(get())
    }
}
