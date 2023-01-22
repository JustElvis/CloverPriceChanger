package com.example.shkvarla.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shkvarla.database.model.UpdateItem

@Dao
interface UpdateItemDao {
    @Query("SELECT * FROM report_changed_price ORDER BY date DESC")
    suspend fun selectAll(): List<UpdateItem>

    @Insert
    suspend fun insert(items: List<UpdateItem>)
}
