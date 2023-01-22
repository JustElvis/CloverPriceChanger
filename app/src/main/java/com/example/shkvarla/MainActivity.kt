package com.example.shkvarla

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shkvarla.adapter.UpdateItemAdapter
import com.example.shkvarla.databinding.ActivityMainBinding
import com.example.shkvarla.service.ItemService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var activityBinding: ActivityMainBinding
    private val itemService: ItemService by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        activityBinding.swipeRefreshLayout.setOnRefreshListener {
            refreshItems()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshItems()
    }

    private fun refreshItems() {
        CoroutineScope(Dispatchers.Main).launch {
            activityBinding.swipeRefreshLayout.isRefreshing = true
            val items = itemService.getAllUpdateItems()
            activityBinding.recyclerView.apply {
                adapter = UpdateItemAdapter(items)
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
            activityBinding.swipeRefreshLayout.isRefreshing = false
        }
    }
}
