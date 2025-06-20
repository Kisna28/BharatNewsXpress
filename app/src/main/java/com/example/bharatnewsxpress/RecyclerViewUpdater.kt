package com.example.bharatnewsxpress

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bharatnewsxpress.data.AppDatabase
import kotlinx.coroutines.launch

class RecyclerViewUpdater(private val context: Context,
                          private val recyclerView: RecyclerView, private val swipeRefreshLayout: SwipeRefreshLayout) {

    private lateinit var myAdapter: MyAdapter

    fun updateRecyclerView(resultList: List<Article>) {
        val dao = AppDatabase.getInstance(context).readLaterDao()
        myAdapter = MyAdapter(
            context,
            resultList,
            onItemClick = { article ->
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("ARTICLE", article)
                context.startActivity(intent)
            },
            onBookmarkClick = { article ->
                // Save to RoomDB
                (context as? MainActivity)?.lifecycleScope?.launch {
                    dao.insert(article.toEntity())
                    Toast.makeText(context, "Saved to Read Later", Toast.LENGTH_SHORT).show()
                }
            }
        )

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = myAdapter
             swipeRefreshLayout.isRefreshing = false

    }
}