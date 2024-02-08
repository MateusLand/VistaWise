package com.example.vistawise

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Start the Login activity
            val intent = Intent(this@SplashActivity, Login::class.java)
            startActivity(intent)

            // Finish the splash activity
            finish()
        }, 1000) // 2000 milliseconds (3 seconds) delay
    }
}
