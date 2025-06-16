package com.example.bharatnewsxpress

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataManager(private val mainActivity: MainActivity) {
    private var apiKey = "92a6e4a6380c46219869c3c3d03f7cd6"

    lateinit var currentData: List<Article>
    val retrofit = RetrofitInstance.api

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchData(news: String) {
        mainActivity.binding.progressBar.visibility = View.VISIBLE

        Log.d("NewsFetch", "✅ API call started")

        val formattedDate: String = FromDate().dateApi()
        // 🧠 Check if cached data is still valid for today
        if (news == "India" && NewsCache.cachedArticles != null && NewsCache.cachedDate == formattedDate) {
            // Use cached data
            val cached = NewsCache.cachedArticles!!
            mainActivity.updateRecyclerView(cached)
            mainActivity.binding.progressBar.visibility = View.GONE
            mainActivity.swipeRefreshLayout.isRefreshing = false
            return
        }
        val response = retrofit.getNewsData(news, formattedDate, apiKey)
        response.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()
                val resultList = responseBody?.articles ?: emptyList()

                // Filter and sort articles
                val filteredAndSortedArticles = resultList
                    .filter { article ->
                        article.publishedAt.startsWith(formattedDate)
                    }
                    .sortedByDescending { it.publishedAt }

                currentData = filteredAndSortedArticles

                // Save to cache only if it's today's data and query is default
                if (news == "India") {  // or whatever your default query is
                    NewsCache.cachedArticles = filteredAndSortedArticles
                    NewsCache.cachedDate = formattedDate
                }

                mainActivity.updateRecyclerView(filteredAndSortedArticles)
                mainActivity.binding.progressBar.visibility = View.GONE


                mainActivity.swipeRefreshLayout.isRefreshing = false
                Log.d(
                    "NewsFetch",
                    "Articles fetched for \"$news\": ${filteredAndSortedArticles.size}"
                )

            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                // Handle failure
                mainActivity.swipeRefreshLayout.isRefreshing = false
                mainActivity.binding.progressBar.visibility = View.GONE
                Log.d("NewsFetch", "❌ API call failed: ${t.message}")

            }
        })
    }

}
