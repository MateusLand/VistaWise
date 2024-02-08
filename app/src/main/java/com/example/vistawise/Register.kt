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

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val editTextEmail = findViewById<TextInputEditText>(R.id.email)
        val editTextPassword = findViewById<TextInputEditText>(R.id.password)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val buttonReg = findViewById<Button>(R.id.btn_register)
        val loginNow = findViewById<TextView>(R.id.loginNow)
        val editTextName = findViewById<TextInputEditText>(R.id.name)

        // Set click listener for loginNow TextView
        loginNow.setOnClickListener {
            val intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
            // Optional: close the current activity
            // finish()
        }

        buttonReg.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val name = editTextName.text.toString()

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                // Show an error message or handle the empty fields as needed
                Toast.makeText(this, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            buttonReg.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE

            // Check if a user is already signed in
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // If a user is already signed in, navigate to the main activity
                navigateToMainActivity()
            } else {
                // If no user is signed in, attempt to create a new account
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.INVISIBLE
                        buttonReg.visibility = View.VISIBLE

                        if (task.isSuccessful) {
                            // Registration is successful, navigate to the main activity
                            navigateToMainActivity()
                            Toast.makeText(
                                this@Register,
                                "Account Created.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // If registration fails, display a message to the user.
                            Log.e("Registration Error", "Registration failed", task.exception)
                            Toast.makeText(
                                this@Register,
                                "Registration failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already signed in, redirect to MainActivity
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@Register, MainActivity::class.java)
        startActivity(intent)
        // Optional: close the current activity
        // finish()
    }
}

