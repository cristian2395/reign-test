package com.example.reigntest.service.BD

import com.example.reigntest.service.BD.Entity.*

interface DatabaseHelper {
    suspend fun getHits(): List<Hit>
    suspend fun insertAll(hit: List<Hit>)
    suspend fun deleteDataH()

    suspend fun getAllD(): List<DeletHit>
    suspend fun insertD(hit: DeletHit)
}