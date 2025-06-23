package com.example.bharatnewsxpress

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bharatnewsxpress.data.AppDatabase
import com.example.bharatnewsxpress.databinding.ActivityReadLaterBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReadLaterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadLaterBinding
    private lateinit var adapter: ReadLaterAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadLaterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.selectedItemId = R.id.nav_saved
        val dao = AppDatabase.getInstance(this).readLaterDao()

        adapter = ReadLaterAdapter(
            articles = emptyList(),
            onItemClick = { articleEntity ->
                val article = articleEntity.toArticle()
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("ARTICLE", article)
                startActivity(intent)
            },
            onBookmarkClick = { article ->
                lifecycleScope.launch {
                    dao.delete(article)
                    Toast.makeText(this@ReadLaterActivity, "Removed", Toast.LENGTH_SHORT).show()
                }
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            dao.getAll().collectLatest { list ->
                adapter.updateList(list)
            }
        }

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_news -> {
                    val intent = Intent(this, MainActivity::class.java)
                    val options = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                    startActivity(intent, options.toBundle())
                    finish()
                    true
                }

                R.id.nav_saved -> true // Already here
                else -> false
            }
        }
    }
}