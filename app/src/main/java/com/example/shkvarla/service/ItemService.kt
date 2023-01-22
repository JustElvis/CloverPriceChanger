package com.example.shkvarla.service

import com.example.shkvarla.database.MyDatabase
import com.example.shkvarla.database.model.UpdateItem

class ItemService(
    private val database: MyDatabase
    ) {
    suspend fun getAllUpdateItems(): List<UpdateItem> {
        return database
            .updateItemDao()
            .selectAll()
    }
}
