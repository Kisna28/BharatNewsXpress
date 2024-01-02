package com.example.bharatnewsxpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
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

}