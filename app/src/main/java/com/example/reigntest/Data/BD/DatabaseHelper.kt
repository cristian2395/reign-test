package com.example.reigntest.Data.BD

import com.example.reigntest.Data.BD.Entity.*

interface DatabaseHelper {
    suspend fun getHits(): List<Hit>
    suspend fun insertAll(hit: List<Hit>)
    suspend fun deleteDataH()

    suspend fun getAllD(): List<DeletHit>
    suspend fun insertD(hit: DeletHit)
}