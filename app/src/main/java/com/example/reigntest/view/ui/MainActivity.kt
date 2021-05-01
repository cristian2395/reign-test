package com.example.reigntest.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reigntest.view.callback.SwipeToDeleteCallback
import com.example.reigntest.view.adapter.HitsAdapter
import com.example.reigntest.databinding.ActivityMainBinding
import com.example.reigntest.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerViewAdapter: HitsAdapter
    lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = MainViewModel(this.application)

        binding.swipeRefreshLayout.isRefreshing = true
        binding.recyclerHit.layoutManager = LinearLayoutManager(this)


        binding.swipeRefreshLayout.setOnRefreshListener {
            model.peticionData()
        }
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerHit.adapter as HitsAdapter
                adapter.getArrayList()[viewHolder.adapterPosition].objectID?.let {
                    model.deletHitBD(it)
                }
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerHit)


        //Live Data
        model.listHits.observeForever {
            recyclerViewAdapter = HitsAdapter(it)
            binding.recyclerHit.adapter = recyclerViewAdapter
        }
        model.isRefreshing.observeForever {
            binding.swipeRefreshLayout.isRefreshing = it
        }
        model.msgSnack.observeForever {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show();
        }

        model.getHitBD()
    }
}