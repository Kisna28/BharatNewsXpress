package com.example.bharatnewsxpress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bharatnewsxpress.data.ArticleEntity
import com.example.bharatnewsxpress.databinding.ItemVerticallyReadLaterBinding
import com.squareup.picasso.Picasso

class ReadLaterAdapter(
    private var articles: List<ArticleEntity>,
    private val onItemClick: (ArticleEntity) -> Unit,
    private val onBookmarkClick: (ArticleEntity) -> Unit,
    ) : RecyclerView.Adapter<ReadLaterAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemVerticallyReadLaterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemVerticallyReadLaterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        with(holder.binding) {
            titleRl.text = article.title
            sourceRl.text = article.sourceName
            dateRl.text = article.publishedAt
            if (!article.urlToImage.isNullOrEmpty()) {
                Picasso.get().load(article.urlToImage).into(holder.binding.imageRl)
            } else {
                holder.binding.imageRl.setImageResource(R.drawable.searchviewshape)
            }

            root.setOnClickListener {
                onItemClick(article)
            }
            // Remove from bookmark
            bookMarkRl.setImageResource(R.drawable.bookmark_filled)
            bookMarkRl.setOnClickListener {
                onBookmarkClick(article) // remove
            }
        }
    }

    fun updateList(newList: List<ArticleEntity>) {
        articles = newList
        notifyDataSetChanged()
    }
}
