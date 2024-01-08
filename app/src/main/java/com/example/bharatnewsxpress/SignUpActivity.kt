package com.example.bharatnewsxpress

import android.content.ContentValues.TAG
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
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth


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

        binding.buttonFacebookLogin.setOnClickListener {
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
        // binding.buttonFacebookLogin.setReadPermissions("email", "public_profile")
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))

        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    val graphRequest =
                        GraphRequest.newMeRequest(loginResult.accessToken) { obj, response ->
                            try {
                                if (obj != null) {
                                    if (obj.has("id")) {
                                        Log.d("FACEBOOKDATA", obj.getString("name"))
                                        Log.d("FACEBOOKDATA", obj.getString("email"))
                                        Log.d("FACEBOOKDATA",obj.getString("picture"))
                                        if (FirebaseAuth.getInstance().currentUser != null) {
                                            // User is already authenticated, go to the main activity
                                            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                            Toast.makeText(baseContext, "Facebook Successfully", Toast.LENGTH_SHORT).show()
                                        } else {
                                            // User is not authenticated, proceed with the authentication process
                                            // Call handleFacebookAccessToken to complete the authentication
                                            handleFacebookAccessToken(loginResult.accessToken)
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                            }
                        }
                    val param = Bundle()
                    param.putString("fields", "name,email,id,picture.type(large)")
                    graphRequest.parameters = param
                    graphRequest.executeAsync()

                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }
                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                }
            },
        )
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = FirebaseAuth.getInstance().currentUser
                   /* if (user != null) {

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }*/
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val GOOGLE_SIGN_IN_REQUEST_CODE = 9001
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            googleAuthManager.handleSignInResult(data)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

}


