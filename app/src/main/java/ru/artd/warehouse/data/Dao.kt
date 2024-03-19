package ru.artd.warehouse.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertWarehous(warehouse: Warehouse)
    @Query("SELECT * FROM warehouse")
    fun getAllWarehouses(): Flow<List<Warehouse>>
}