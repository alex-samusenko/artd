package ru.artd.warehouse.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "warehouse")
data class Warehouse(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    @ColumnInfo(name="name")
    var name: String,
    @ColumnInfo(name="code")
    var code: String,
)
