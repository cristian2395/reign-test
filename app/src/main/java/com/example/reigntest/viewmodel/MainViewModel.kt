package com.example.reigntest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.reigntest.service.Api.Api
import com.example.reigntest.service.BD.DatabaseBuilder
import com.example.reigntest.service.BD.DatabaseHelperImpl
import com.example.reigntest.service.BD.Entity.DeletHit
import com.example.reigntest.service.BD.Entity.Hit
import com.example.reigntest.service.Models.Item
import com.example.reigntest.service.Models.ResponseRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val api = Api()
    private var dbHelper: DatabaseHelperImpl = DatabaseHelperImpl(DatabaseBuilder.getInstance(application))
    val listHits: MutableLiveData<ArrayList<Hit>> by lazy { MutableLiveData<ArrayList<Hit>>() }
    val isRefreshing: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val msgSnack: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun peticionData() = CoroutineScope(Dispatchers.IO).launch {
        isRefreshing.postValue(true)
        withContext(Dispatchers.Main) {
            val result: ResponseRequest<Item?> = api.GET("search_by_date?query=mobile")
            if (result.status) {
                listHits.postValue(filterData(ArrayList<Hit>(result.data?.hits!!), getHitDeletBD()))
                dbHelper.deleteDataH()
                dbHelper.insertAll(result.data.hits)
            } else {
                msgSnack.postValue(result.message)
            }
            isRefreshing.postValue(false)
        }
    }

    private fun filterData(item: ArrayList<Hit>, delete: ArrayList<DeletHit>): ArrayList<Hit> {
        return if (delete.size > 0) {
            ArrayList<Hit>(item.filter { api ->
                delete.find{ api.objectID == it.objectID }?.let { false } ?:run { true }
            })
        } else {
            item
        }
    }

    private suspend fun getHitDeletBD(): ArrayList<DeletHit> {
        return ArrayList<DeletHit>(dbHelper.getAllD())
    }

    fun getHitBD() = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            listHits.postValue(filterData(ArrayList<Hit>(dbHelper.getHits()), getHitDeletBD()))
            peticionData()
        }
    }

    fun deletHitBD(id: String) = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            dbHelper.insertD(DeletHit(id))
        }
    }
}