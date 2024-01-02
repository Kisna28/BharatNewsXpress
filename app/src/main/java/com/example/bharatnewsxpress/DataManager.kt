package com.example.bharatnewsxpress

import android.os.Build
import android.util.Log
import android.widget.TextView

import androidx.annotation.RequiresApi
import com.google.android.gms.common.api.internal.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataManager(private val mainActivity: MainActivity) {
    private var apiKey ="92a6e4a6380c46219869c3c3d03f7cd6"

    lateinit var currentData: List<Article>
    val retrofit = RetrofitInstance.api

    @RequiresApi(Build.VERSION_CODES.O)

    fun fetchData(news:String) {


        val formattedDate: String = fromdate().dateApi()

        val response =
            retrofit.getNewsData(news, formattedDate, apiKey)

        response.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()
                val resultList = responseBody?.articles!!

                if (!::currentData.isInitialized || !currentData.contentEquals(resultList)) {
                    // Data has changed, update adapter and show a toast
                    currentData = resultList
                    mainActivity.updateRecyclerView(resultList)
                    // Assuming you have a swipeRefreshLayout in your MainActivity
                    mainActivity.swipeRefreshLayout.isRefreshing = false
                    // Show a toast
                    /*   val toast = mainActivity
                       val print= toast.Toast.makeText(this@DataManager, "Data Refreshed", Toast.LENGTH_SHORT)*/
                } else {
                    // Data has not changed, just stop the refreshing animation
                    mainActivity.swipeRefreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("DataManager", "OnFailure: ${t.message}")

            }
        })
    }

}
