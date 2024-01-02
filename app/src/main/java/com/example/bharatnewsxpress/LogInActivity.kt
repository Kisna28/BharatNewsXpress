package com.example.bharatnewsxpress

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.bharatnewsxpress.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private val PREFS_NAME = "ThemePrefs"
    private val PREFS_KEY_THEME = "theme"


    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val switch = findViewById<Switch>(R.id.switchTheme)
        switch.isChecked = getThemePreference()
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveThemePreference(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                buttonView.text = "Night Mode"
            } else {
                saveThemePreference(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                buttonView.text = "Light Mode"
            }

        }
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
            binding.textViewlog.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()



            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                        } else {
                            Log.e("LoginActivity", "Login failed", it.exception)
                            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Empty field is not allowed", Toast.LENGTH_LONG).show()
            }

            binding.password.clearFocus()

        }

    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = firebaseAuth.getCurrentUser()
        updateUI(currentUser)
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            // User is signed in, meaning the user is registered
            // You can perform actions for a signed-in user here
            // For example, you might navigate to a home screen or display a welcome message
            // You can also retrieve user information using currentUser
            val uid = currentUser.uid
            val email = currentUser.email
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } else {
            // User is not signed in, meaning the user is not registered
            // You can perform actions for a non-signed-in user here
            // For example, you might display a login or registration screen
        }
    }
    private fun applyTheme() {
        // Retrieve the saved theme preference and set the theme
        val isDarkMode = getThemePreference()
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    private fun saveThemePreference(isDarkMode: Boolean) {
        // Save the theme preference
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(PREFS_KEY_THEME, isDarkMode)
        editor.apply()
    }
        private fun getThemePreference(): Boolean {
            val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return prefs.getBoolean(PREFS_KEY_THEME, false) // Default to false (light mode) if not found
        }

}


