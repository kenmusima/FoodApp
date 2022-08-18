package com.foodapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.foodapp.MainActivity
import com.foodapp.R
import com.foodapp.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener { validateCredentials() }

    }

    private fun validateCredentials() {
        val email = binding.emailRegister.editText?.text.toString().trim()
        val password = binding.passwordRegister.editText?.text.toString().trim()
        val confirmPassword = binding.confirmPassword.editText?.text.toString().trim()
        val username = binding.usernameRegister.editText?.text.toString().trim()

        val isEmailValid =
            if (TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailRegister.error = "Please enter correct email format"
                false
            } else {
                true
            }

        val isPasswordValid = when {
            TextUtils.isEmpty(password) -> {
                binding.passwordRegister.error = "Please enter a password"
                false
            }
            confirmPassword.length < 6 -> {
                binding.passwordRegister.error = "Password length should be greater than 6"
                false
            }
            else -> {
                true
            }
        }

        val isConfirmPasswordValid = when {
            TextUtils.isEmpty(confirmPassword) -> {
                binding.confirmPassword.error = "Please enter a password"
                false
            }
            confirmPassword.length < 6 -> {
                binding.confirmPassword.error = "Password length should be greater than 6"
                false
            }
            confirmPassword != password -> {
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }

        val isUsernameValid = if (TextUtils.isEmpty(username)) {
            binding.usernameRegister.error = "Username cannot be empty"
            false
        } else {
            true
        }

        if (isEmailValid && isPasswordValid && isConfirmPasswordValid && isUsernameValid) {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.emailRegister.editText?.text.toString().trim()
        val password = binding.passwordRegister.editText?.text.toString().trim()
        val username = binding.usernameRegister.editText?.text.toString().trim()

        binding.progressBarRegister.visibility = View.VISIBLE
        binding.btnRegister.visibility = View.GONE

        val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(username).build()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                binding.progressBarRegister.visibility = View.GONE
                binding.btnRegister.visibility = View.VISIBLE
                Toast.makeText(this, "User Registration Failed.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            it.user?.updateProfile(profileUpdate)
        }
    }
}