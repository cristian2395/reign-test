package com.example.reigntest.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reigntest.Data.Api.Api
import com.example.reigntest.Data.BD.DatabaseBuilder
import com.example.reigntest.Data.BD.DatabaseHelperImpl
import com.example.reigntest.Data.BD.Entity.DeletHit
import com.example.reigntest.Data.BD.Entity.Hit
import com.example.reigntest.Data.Models.Item
import com.example.reigntest.Data.Models.ResponseRequest
import com.example.reigntest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class MainActivity() : AppCompatActivity() {

    private val api = Api()
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelperImpl
    lateinit var recyclerViewAdapter: HitsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.swipeRefreshLayout.isRefreshing = true

        dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))

        binding.recyclerHit.layoutManager = LinearLayoutManager(this)
        binding.swipeRefreshLayout.setOnRefreshListener { peticion() }

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerHit.adapter as HitsAdapter
                adapter.getArrayList()[viewHolder.adapterPosition].objectID?.let { deletHitBD(it) }
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerHit)

        getHitBD()
    }

    fun peticion() = CoroutineScope(Dispatchers.IO).launch {
        binding.swipeRefreshLayout.isRefreshing = true
        withContext(Dispatchers.Main) {
            val result: ResponseRequest<Item?> = api.GET("search_by_date?query=mobile")
            if (result.status) {
                val data = filterData(ArrayList<Hit>(result.data?.hits!!), getHitDeletBD())
                recyclerViewAdapter = HitsAdapter(data)
                binding.recyclerHit.adapter = recyclerViewAdapter
                dbHelper.deleteDataH()
                dbHelper.insertAll(result.data.hits)
            } else {
                Snackbar.make(binding.root, result.message, Snackbar.LENGTH_LONG).show();
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    fun filterData(item: ArrayList<Hit>, delete: ArrayList<DeletHit>): ArrayList<Hit> {
        return if (delete.size > 0) {
            ArrayList<Hit>(item.filter { api ->
                delete.find{ api.objectID == it.objectID }?.let { false } ?:run { true }
            })
        } else {
            item
        }
    }

    suspend fun getHitDeletBD(): ArrayList<DeletHit> {
        return ArrayList<DeletHit>(dbHelper.getAllD())
    }

    fun getHitBD() = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            val data = filterData(ArrayList<Hit>(dbHelper.getHits()), getHitDeletBD())
            recyclerViewAdapter = HitsAdapter(data)
            binding.recyclerHit.adapter = recyclerViewAdapter
            peticion()
        }
    }

    fun deletHitBD(id: String) = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            dbHelper.insertD(DeletHit(id))
        }
    }
}