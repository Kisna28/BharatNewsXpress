package com.example.bharatnewsxpress.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize

@Entity(tableName = "read_later_articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val publishedAt: String,
    val sourceName: String,
    val urlToImage: String?,
    val description: String?
):Parcelable
