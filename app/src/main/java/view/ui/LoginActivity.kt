package view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vistawise.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import network.UserService
import repository.UserRepository
import viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        LoginViewModelFactory(UserRepository(UserService(FirebaseAuth.getInstance()))) // Provide an instance of UserRepository
    }

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
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Password reset email sent. Check your email.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Failed to send password reset email.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Enter your registered email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Set click listener for registerNow TextView
        binding.registerNow.setOnClickListener {
            goToRegister()
        }

        binding.btnLogin.setOnClickListener {

            handleBtnLoginClick()
        }

        // Observe loginStatus LiveData
        loginViewModel.loginStatus.observe(this) { isLoggedIn ->
            loading(false)

            if (isLoggedIn) {
                Toast.makeText(
                    this@LoginActivity,
                    "Login Successful.",
                    Toast.LENGTH_SHORT,
                ).show()

                goToHome()

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    this@LoginActivity,
                    "Authentication failed, please register first.",
                    Toast.LENGTH_SHORT,
                ).show()

                Log.e("Login Error", "Authentication failed")

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
