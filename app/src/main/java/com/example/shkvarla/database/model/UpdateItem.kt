package com.example.shkvarla.database.model

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "report_changed_price")
class UpdateItem (
    @ColumnInfo(name = "old_price") val oldPrice: Double,
    @ColumnInfo(name = "new_price") val newPrice: Double,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "order_id") val orderId: String,
    @ColumnInfo(name = "item_id") val itemId: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null

    @SuppressLint("SimpleDateFormat")
    fun getCorrectDate():String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(date)
    }

    fun getCorrectOldPrice(): String {
        return "$oldPrice$"
    }

    fun getCorrectNewPrice(): String {
        return "$newPrice$"
    }
}
