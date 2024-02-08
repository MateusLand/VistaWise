package com.example.vistawise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

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
                                this@Login,
                                "Password reset email sent. Check your email.",
                                Toast.LENGTH_SHORT
                                ).show()
                        } else {
                            Toast.makeText(
                                this@Login,
                                "Failed to send password reset email.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this@Login,
                    "Enter your registered email address.",
                    Toast.LENGTH_SHORT
                    ).show()
            }
        }

        // Set click listener for registerNow TextView
        registerNow.setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
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

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.INVISIBLE
                    buttonLogin.visibility = View.VISIBLE

                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@Login,
                            "Login Successful.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        // Create an intent to navigate to the MainActivity
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Optional: close the current activity

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@Login,
                            "Authentication failed, please register first.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        Log.e("Login Error", "Authentication failed", task.exception)

                    }
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
