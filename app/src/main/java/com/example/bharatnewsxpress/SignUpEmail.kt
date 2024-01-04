package com.example.bharatnewsxpress

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class SignUpEmail(private val context: Context,private  val activity : Activity) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    fun signupemail(
        email: String,
        password: String,
        confirmPassword: String,

    ) {

        if (validateInput(email, password, confirmPassword)) {

            if (password == confirmPassword) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(activity, MainActivity::class.java)
                            activity.startActivity(intent)
                            activity.finish()
                            Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Password Is not matching", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Enter Vailed Email Addressee", Toast.LENGTH_SHORT).show()
            }
            return true
        } else {
            Toast.makeText(context, "Empty field is not allowed", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}



