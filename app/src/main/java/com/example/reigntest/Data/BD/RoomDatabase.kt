package com.example.reigntest.Data.BD

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reigntest.Data.BD.DAO.*
import com.example.reigntest.Data.BD.Entity.*

@Database(entities = [Hit::class, DeletHit::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hitDao(): HitDAO
    abstract fun deletHitDAO(): DeletHitDAO
}


object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "mindorks-example-coroutines"
        ).build()

}