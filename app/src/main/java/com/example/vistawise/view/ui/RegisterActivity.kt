package com.example.vistawise.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vistawise.databinding.ActivityRegisterBinding
import com.example.vistawise.viewmodel.RegisterViewModel
import com.example.vistawise.viewmodel.UserRegistrationResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHaveAccount.text


        // Set click listener for loginNow TextView
        binding.loginNow.setOnClickListener {
            goToLogin()
        }

        // Set click listener for btnRegister
        binding.btnRegister.setOnClickListener {
            handleBtnRegisterClick()
        }

        // Observe registrationStatus LiveData
        registerViewModel.userRegistrationStatus.observe(this) { userRegistrationStatus ->
            handleScreenState(userRegistrationStatus)
        }
    }

    private fun handleScreenState(userRegistrationStatus: UserRegistrationResult) {
        when (userRegistrationStatus) {
            is UserRegistrationResult.Loading -> {
                loading(true)
            }

            is UserRegistrationResult.UserRegistrationSuccess -> {
                loading(false)
                Toast.makeText(
                    this@RegisterActivity,
                    "Registration Successful.",
                    Toast.LENGTH_SHORT
                ).show()
                goToMain()
            }

            is UserRegistrationResult.UserRegistrationError -> {
                loading(false)
                Toast.makeText(
                    this@RegisterActivity,
                    "Failed to register user.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UserRegistrationResult.UserAlreadyExists -> {
                loading(false)
                Toast.makeText(
                    this@RegisterActivity,
                    "User already exists.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    // Helper function for loading
    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.btnRegister.visibility = View.VISIBLE
        }
    }

    // Helper function for going to the MainActivity
    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Helper function for going to the LoginActivity
    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Helper function for handling registration button click
    private fun handleBtnRegisterClick() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        registerViewModel.registerUser(email, password)
    }

}
