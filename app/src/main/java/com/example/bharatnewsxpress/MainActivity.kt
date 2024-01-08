package com.example.bharatnewsxpress

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bharatnewsxpress.databinding.ActivityMainBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var currentData: List<Article>
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var myAdapter: MyAdapter
    private lateinit var dataManager: DataManager
    private var currentQuery: String = "India"


    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
    }

    private fun SearchNews() {
        val searchView = binding.search
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener,
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
        RecyclerViewUpdater(this,recyclerView,swipeRefreshLayout).updateRecyclerView(resultList)
        Toast.makeText(this, "Data Refreshed", Toast.LENGTH_SHORT).show()
    }


    val apiDateString = "2023-01-01T12:34:56Z"
    val formattedDate  = DateUtils.formatApiDate(apiDateString)
}



