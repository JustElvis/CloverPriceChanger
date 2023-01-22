package com.example.shkvarla.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shkvarla.database.dao.UpdateItemDao
import com.example.shkvarla.database.model.UpdateItem

@Database(entities = [UpdateItem::class], version = 3, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {
    abstract fun updateItemDao(): UpdateItemDao

}
