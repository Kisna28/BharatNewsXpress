package com.example.bharatnewsxpress

import com.example.bharatnewsxpress.data.ArticleEntity

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        url = this.url ?: "",                                // url is nullable
        title = this.title ?: "No Title",
        publishedAt = this.publishedAt,
        sourceName = this.source?.name ?: "Unknown Source",  // handle null source
        urlToImage = this.urlToImage?:"",
        description = this.description

    )
}