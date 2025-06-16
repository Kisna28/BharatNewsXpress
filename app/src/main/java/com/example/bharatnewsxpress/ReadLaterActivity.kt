package com.example.bharatnewsxpress

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bharatnewsxpress.databinding.ActivityMainBinding
import com.example.bharatnewsxpress.databinding.ActivityReadLaterBinding

class ReadLaterActivity : AppCompatActivity() {
    val binding: ActivityReadLaterBinding by lazy { ActivityReadLaterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bottomNav.selectedItemId = R.id.nav_saved

       binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_news -> {
                    val intent = Intent(this, MainActivity::class.java)
                    val options = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                    startActivity(intent,options.toBundle())
                    finish()
                    true
                }

                R.id.nav_saved -> true // Already here
                else -> false
            }
        }
    }
}