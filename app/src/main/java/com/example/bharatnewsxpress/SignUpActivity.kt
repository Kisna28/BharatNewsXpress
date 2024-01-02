package com.example.bharatnewsxpress

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bharatnewsxpress.databinding.ActivitySignUpBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.Arrays


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleAuthManager: GoogleAuthManager
    private lateinit var signUpEmail: SignUpEmail
    private lateinit var callbackManager: CallbackManager
    private val EMAIL = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //   JavaHelper.printHashKey(this)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth = Firebase.auth
        googleAuthManager = GoogleAuthManager(this)
        signUpEmail = SignUpEmail(this, this)
        callbackManager = CallbackManager.Factory.create()

        binding.textViewsu.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
        binding.textViewsign.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
        binding.google.setOnClickListener {
            googleAuthManager.signInWithGoogle()
        }
        val loginButton = binding.facebook

     //****   loginButton.setReadPermissions(Arrays.asList(EMAIL))

        binding.facebook.setOnClickListener {

            signInWithFacebook()
        }

        binding.buttonSignUp.setOnClickListener {
            val emailsu = binding.editTextEmail.text.toString()
            val passsu = binding.editTextPassword.text.toString()
            val confirmpassu = binding.editTextConfirmPassword.text.toString()
            SignUpEmail(this, this).signupemail(emailsu, passsu, confirmpassu)
        }
    }
    private fun signInWithFacebook() {
        val loginButton = LoginButton(this)
        loginButton.setPermissions(EMAIL)

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                // Handle canceled login
            }

            override fun onError(error: FacebookException) {
                // Handle error
                Log.d("FacebookLogin", "Error: ${error.message}")
            }
        })

        loginButton.performClick()
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser
                    // Navigate to the main activity or perform other actions
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Facebook Authentication Failed", Toast.LENGTH_SHORT).show()
                    // If sign in fails, display a message to the user.
                    // You can handle the error based on your requirements.
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleAuthManager.handleSignInResult(data)
    }
}


