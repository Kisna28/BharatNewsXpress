package com.example.bharatnewsxpress

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {


    @GET("everything")
    fun getNewsData(
        @Query("q") news:String,
        @Query("from") fromDate: String, // Format: "yyyy-MM-dd"
        @Query("apiKey") apikey: String
    ):Call<MyData>
}
