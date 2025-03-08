package com.example.bharatnewsxpress

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataManager(private val mainActivity: MainActivity) {
    private var apiKey ="92a6e4a6380c46219869c3c3d03f7cd6"

    lateinit var currentData: List<Article>
    val retrofit = RetrofitInstance.api

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchData(news: String) {
        val formattedDate: String = FromDate().dateApi()
        val response = retrofit.getNewsData(news, formattedDate, apiKey)

        response.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()
                val resultList = responseBody?.articles ?: emptyList()

                // Filter and sort articles
                val filteredAndSortedArticles = resultList
                    .filter { article ->
                        // Ensure the article date matches the desired date
                        article.publishedAt.startsWith(formattedDate)
                    }
                    .sortedByDescending { article ->
                        // Sort by publishedAt descending (latest news first)
                        article.publishedAt
                    }

                if (!::currentData.isInitialized || !currentData.contentEquals(
                        filteredAndSortedArticles
                    )
                ) {
                    // Data has changed, update adapter
                    currentData = filteredAndSortedArticles
                    mainActivity.updateRecyclerView(filteredAndSortedArticles)
                    mainActivity.swipeRefreshLayout.isRefreshing = false
                } else {
                    // No changes in data
                    mainActivity.swipeRefreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                // Handle failure
            }
        })
    }

}
