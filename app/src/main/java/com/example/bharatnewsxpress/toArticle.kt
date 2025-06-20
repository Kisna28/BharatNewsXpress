package com.example.bharatnewsxpress

import com.example.bharatnewsxpress.data.ArticleEntity

fun ArticleEntity.toArticle(): Article {
    return Article(
        author = null,
        content = null,
        description = this.description,
        publishedAt = this.publishedAt,
        source = Source(id = "", name = ""),
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}