package com.example.vistawise.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vistawise.databinding.ActivityLoginBinding
import com.example.vistawise.viewmodel.LoginViewModel
import com.example.vistawise.viewmodel.UserAuthResult
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set click listener for forgotPassword TextView
        binding.forgotPassword.setOnClickListener {
            val email = binding.email.text.toString()

            if (email.isNotEmpty()) {
                loginViewModel.resetPassword(email)
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Enter your registered email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //  click listener for registerNow TextView
        binding.registerNow.setOnClickListener {
            goToRegister()
        }

        //  click listener for btnLogin
        binding.btnLogin.setOnClickListener {

            handleBtnLoginClick()
        }

        // Observe loginStatus LiveData
        loginViewModel.userAuthStatus.observe(this) { userAuthResult ->

            handleScreenState(userAuthResult)
        }

    }

    // Helper function for handling screen state
    private fun handleScreenState(userAuthResult: UserAuthResult) {
        when (userAuthResult) {
            is UserAuthResult.Loading -> {
                loading(true)
            }

            is UserAuthResult.UserAuthSuccess -> {
                loading(false)
                Toast.makeText(
                    this@LoginActivity,
                    "Login Successful.",
                    Toast.LENGTH_SHORT,
                ).show()
                goToHome()
            }

            is UserAuthResult.PasswordResetError -> {
                loading(false)
                Toast.makeText(
                    this@LoginActivity,
                    "Failed to send password reset email.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            UserAuthResult.PasswordResetSuccess -> {
                loading(false)
                Toast.makeText(
                    this@LoginActivity,
                    "Password reset email sent. Check your email.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UserAuthResult.UserAuthError -> {
                loading(false)
                Toast.makeText(
                    this@LoginActivity,
                    "Authentication failed, please register first.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    // Helper function for loading
    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.btnLogin.visibility = View.VISIBLE
        }
    }

    // Helper function for going to home activity
    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Helper function for going to register activity
    private fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Helper function for handling login
    private fun handleBtnLoginClick() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            // Show an error message or handle the empty fields as needed
            Toast.makeText(this, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        loading(true)

        loginViewModel.loginUser(email, password)
    }
}
