package com.example.foodtime_compose0518

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "stock_table")
data class StockTable(
    @PrimaryKey(autoGenerate = true)
    val stockitemId: Int = 0,

    @ColumnInfo(name = "stockitem_name")
    val stockitemName: String,

    @ColumnInfo(name = "login_date")
    val loginDate: Long,

    @ColumnInfo(name = "expiry_date")
    val expiryDate: Long,

    @ColumnInfo(name = "uuid")
    val uuid: String,
) {
    // 无参数构造函数
    constructor() : this(0, "", 0, 0, "")
}
