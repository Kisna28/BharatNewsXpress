package com.example.bharatnewsxpress

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class RecyclerViewUpdater(private val context: Context,
                          private val recyclerView: RecyclerView, private val swipeRefreshLayout: SwipeRefreshLayout) {

    private lateinit var myAdapter: MyAdapter

    fun updateRecyclerView(resultList: List<Article>) {
        myAdapter = MyAdapter(context, resultList)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout.isRefreshing = false
    }
}