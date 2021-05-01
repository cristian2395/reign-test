package com.example.reigntest.service.BD.DAO

import androidx.room.*
import com.example.reigntest.service.BD.Entity.Hit

@Dao
interface HitDAO {

    @Query("SELECT * FROM " + Hit.TABLE_NAME + " ORDER BY hit_id")
    suspend fun getAll(): List<Hit>

    @Insert
    suspend fun insertAll(hit: List<Hit>)

    @Delete
    suspend fun delete(hit: Hit)

    @Query("DELETE FROM " + Hit.TABLE_NAME)
    suspend fun deleteData()
}