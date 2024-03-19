package ru.artd.warehouse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Warehouse::class], version = 1)
abstract class LocalDb: RoomDatabase() {
    abstract fun getDao(): Dao
    companion object {
        fun getDb(context: Context): LocalDb {
            return Room.databaseBuilder(
                context.applicationContext,
                LocalDb::class.java,
                "warehouse").build()
        }
    }
}