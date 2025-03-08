package com.example.bharatnewsxpress

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
     initializeTheme()

        // checkUser()
        val firebaseAuth=FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        Handler().postDelayed({
            if (currentUser != null) {
                // User is already logged in, open MainActivity
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
            } else {
                // User is not logged in, open LoginActivity
                val intent = Intent(this@SplashScreen, LogInActivity::class.java)
                startActivity(intent)
            }
            finish() // Close the splash activity so that it's not in the back stack
        }, 2000)
    }
    fun initializeTheme() {
        val sharedPreferences = getSharedPreferences("theme_pref", MODE_PRIVATE)
        val theme = sharedPreferences.getString("theme", "system")

        val mode = when (theme) {
            "dark" -> AppCompatDelegate.MODE_NIGHT_YES
            "light" -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

}