package com.foodapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val TAG = LoginActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        binding.signInBtn.setOnClickListener {
            validateCredentials()
        }

        binding.rememberCheckBox.setOnClickListener {
            if (it.isSelected) {
                // TODO: Setup
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //TODO: Set ClientID
            .requestIdToken("")
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleSignIn.setSize(SignInButton.SIZE_STANDARD)
        binding.googleSignIn.setOnClickListener { googleSignIn() }

        binding.registerTxt.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val accountToken = task.result.idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(accountToken, null)

            auth.signInWithCredential(firebaseCredential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(
                        this,
                        "Authentication Failed :${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: ApiException) {

            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun validateCredentials() {
        val email = binding.signInEmail.editText?.text.toString().trim()
        val password = binding.signInPassword.editText?.text.toString().trim()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter credentials", Toast.LENGTH_SHORT).show()
        } else {
            binding.signInBtn.visibility = View.GONE
            binding.signInProgressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        binding.signInProgressBar.visibility = View.GONE
                        binding.signInBtn.visibility = View.VISIBLE
                    } else {
                        binding.signInProgressBar.visibility = View.GONE
                        binding.signInBtn.visibility = View.VISIBLE
                        Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }
    }

    companion object {
        const val RC_SIGN_IN = 100
    }
}