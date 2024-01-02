package com.example.bharatnewsxpress

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.credentials.Credential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthManager(private val activity: Activity) {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    init {
        initializeGoogleSignIn()
    }

    private fun initializeGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
        auth = FirebaseAuth.getInstance()
    }

    fun signInWithGoogle() {
        val signInClient = googleSignInClient.signInIntent
        activity.startActivityForResult(signInClient, RC_SIGN_IN)
    }

    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            signInWithCredential(credential)
        } catch (e: ApiException) {
            Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithCredential(credential: AuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent =Intent(activity,MainActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
                Toast.makeText(activity, "Successfully SignUp", Toast.LENGTH_SHORT).show()
            // Navigate to the next activity or perform other actions
            } else {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        const val RC_SIGN_IN = 9001 // Request code for Google Sign In
    }
}