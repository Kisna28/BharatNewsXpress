package com.example.bharatnewsxpress

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val context: Context, private var resultArrayList: List<Article>,   private val onItemClick: (Article) -> Unit) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_vertically, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = resultArrayList[position]
        holder.title.text = currentItem.title
        holder.title.maxLines = 2
        holder.title.ellipsize = TextUtils.TruncateAt.END
        holder.itemView.setOnClickListener{
            onItemClick(currentItem)
        }

        val formattedDate = DateUtils
        holder.date.text = formattedDate.formatApiDate(currentItem.publishedAt)
        holder.source.text = currentItem.source?.name ?: "Unknown Source"
        Picasso.get().load(currentItem.urlToImage).into(holder.image)

    }
    override fun getItemCount(): Int {
        return resultArrayList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Article>) {
        resultArrayList = newData
        notifyDataSetChanged()

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titlevl)
        val source: TextView = itemView.findViewById(R.id.sourcevl)
        val date: TextView = itemView.findViewById(R.id.date)
        val image: ImageView = itemView.findViewById(R.id.imagevt)
    }
}





