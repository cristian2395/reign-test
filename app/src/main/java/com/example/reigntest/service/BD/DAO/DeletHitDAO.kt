package com.example.reigntest.service.BD.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.reigntest.service.BD.Entity.DeletHit

@Dao
interface DeletHitDAO {

    @Query("SELECT * FROM " + DeletHit.TABLE_NAME)
    suspend fun getAllD(): List<DeletHit>

    @Insert
    suspend fun insertAll(hits: List<DeletHit>)

    @Insert
    suspend fun insertD(hit: DeletHit)

    @Delete
    suspend fun delete(hit: DeletHit)
}