package com.example.bharatnewsxpress

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bharatnewsxpress.databinding.ActivityMainBinding
import java.util.Collections.emptyList

class MainActivity : AppCompatActivity() {

    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var currentData: List<Article> = emptyList()
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var myAdapter: MyAdapter
    private lateinit var dataManager: DataManager
    private var currentQuery: String = "India"


    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNav.selectedItemId = R.id.nav_news
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_news -> true //Alredy here
                R.id.nav_saved -> {
                    val intent = Intent(this, ReadLaterActivity::class.java)
                    val options = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                    startActivity(intent,options.toBundle())
                    finish()
                    true
                }
                else -> false
            }
        }


/*
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout.setOnRefreshListener {
            // Initialize DataManager
            dataManager = DataManager(this)
            // Call fetchData from DataManager
            dataManager.fetchData(currentQuery)

        }
        dataManager = DataManager(this)
        dataManager.fetchData(currentQuery)
        SearchNews()

        // Example: Assuming currentData holds your articles
        myAdapter = MyAdapter(this, currentData) { selectedArticle ->
            // Navigate to DetailActivity with the selected article
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("ARTICLE", selectedArticle)
            }
            startActivity(intent)
        }

        recyclerView.adapter = myAdapter
*/

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.recyclerView)

        dataManager = DataManager(this)

        val formattedDate = FromDate().dateApi()
        if (NewsCache.cachedArticles != null && NewsCache.cachedDate == formattedDate) {
            // ✅ use cached data
            Log.d("NewsFetch", "✅ Data loaded from cache")

            currentData = NewsCache.cachedArticles!!
            updateRecyclerView(currentData)
        } else {
            // ✅ fetch from API
            Log.d("NewsFetch", "🌐 Fetching fresh data from API")

            dataManager.fetchData(currentQuery)
        }

        swipeRefreshLayout.setOnRefreshListener {
            NewsCache.cachedArticles = null
            NewsCache.cachedDate = null
            dataManager.fetchData(currentQuery)
        }

        SearchNews()

    }

    private fun SearchNews() {
        val searchView = binding.search
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    this@MainActivity.currentQuery = query  //Update the current query
                    dataManager.fetchData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    fun updateRecyclerView(resultList: List<Article>) {
        currentData = resultList  // ✅ keep current data
        RecyclerViewUpdater(this, recyclerView, swipeRefreshLayout).updateRecyclerView(resultList)
        Toast.makeText(this, "Data Refreshed", Toast.LENGTH_SHORT).show()
    }


    val apiDateString = "2023-01-01T12:34:56Z"
    val formxattedDate = DateUtils.formatApiDate(apiDateString)
}



