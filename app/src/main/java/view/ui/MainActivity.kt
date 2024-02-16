package view.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import view.adapter.DestinationsAdapter
import com.example.vistawise.R
import com.google.firebase.auth.FirebaseAuth
import model.Destination

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the user is signed in (non-null)
        val currentUser = auth.currentUser

        // Use View Binding or Kotlin Android Extensions for cleaner code
        val userDetailsTextView = findViewById<TextView>(R.id.user_details)
        val signOutButton = findViewById<Button>(R.id.btn_SignOut)
        val rvDestination = findViewById<RecyclerView>(R.id.rv_Destination)

        // Initialize RecyclerView
        rvDestination.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        if (currentUser == null) {
            // If the user is null, navigate to the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: close the current activity
        } else {
            // User is already signed in, proceed with your main activity logic

            // Display user email or perform other actions
            userDetailsTextView.text = "Hello, ${currentUser.email}"

            // Set up sign-out button click listener
            signOutButton.setOnClickListener {
                // Sign out the user
                auth.signOut()

                // Navigate back to the LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Optional: close the current activity
            }

            // Create dummy data for destinations
            val destinations = listOf(
                Destination("London", R.drawable.london),
                Destination("Paris", R.drawable.paris),
                Destination("Budapest", R.drawable.budapest),
                Destination("Rome", R.drawable.rome)

                // Add more destinations as needed
            )

            // Set up RecyclerView adapter
            val adapter = DestinationsAdapter(destinations)
            rvDestination.adapter = adapter
        }
    }
}
