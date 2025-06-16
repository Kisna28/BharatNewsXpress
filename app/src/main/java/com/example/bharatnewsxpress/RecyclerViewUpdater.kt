package com.example.bharatnewsxpress

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class RecyclerViewUpdater(private val context: Context,
                          private val recyclerView: RecyclerView, private val swipeRefreshLayout: SwipeRefreshLayout) {

    private lateinit var myAdapter: MyAdapter

    fun updateRecyclerView(resultList: List<Article>) {
        myAdapter = MyAdapter(context, resultList) { article ->

            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("ARTICLE", article)
            context.startActivity(intent)
        }

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = myAdapter
             swipeRefreshLayout.isRefreshing = false

    }
}