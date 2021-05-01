package com.example.reigntest.Data.BD

import com.example.reigntest.Data.BD.Entity.DeletHit
import com.example.reigntest.Data.BD.Entity.Hit

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getHits(): List<Hit> = appDatabase.hitDao().getAll()
    override suspend fun insertAll(hit: List<Hit>) = appDatabase.hitDao().insertAll(hit)
    override suspend fun deleteDataH() = appDatabase.hitDao().deleteData()
    override suspend fun getAllD(): List<DeletHit> = appDatabase.deletHitDAO().getAllD()
    override suspend fun insertD(hit: DeletHit) = appDatabase.deletHitDAO().insertD(hit)

}