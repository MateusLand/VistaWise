package view.ui

import viewmodel.LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vistawise.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import androidx.activity.viewModels
import network.UserService
import repository.UserRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        LoginViewModelFactory(UserRepository(UserService(FirebaseAuth.getInstance()))) // Provide an instance of UserRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val editTextEmail = findViewById<TextInputEditText>(R.id.email)
        val editTextPassword = findViewById<TextInputEditText>(R.id.password)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val buttonLogin = findViewById<Button>(R.id.btn_login)
        val registerNow = findViewById<TextView>(R.id.registerNow)

        val forgotPasswordLink = findViewById<TextView>(R.id.forgotPassword)
        forgotPasswordLink.setOnClickListener {
            val email = editTextEmail.text.toString()

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
        registerNow.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish() // Optional: close the current activity
        }

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                // Show an error message or handle the empty fields as needed
                Toast.makeText(this, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            buttonLogin.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE

            loginViewModel.loginUser(email, password)
        }

        loginViewModel.loginStatus.observe(this) { isLoggedIn ->
            progressBar.visibility = View.INVISIBLE
            buttonLogin.visibility = View.VISIBLE

            if (isLoggedIn) {
                Toast.makeText(
                    this@LoginActivity,
                    "Login Successful.",
                    Toast.LENGTH_SHORT,
                ).show()

                // Create an intent to navigate to the MainActivity
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional: close the current activity

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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: close the current activity
        }
    }

}
