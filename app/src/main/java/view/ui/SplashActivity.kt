package view.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vistawise.R
import com.google.firebase.auth.FirebaseAuth
import network.UserService
import repository.UserRepository
import viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        SplashViewModelFactory(UserRepository(UserService(FirebaseAuth.getInstance()))) // Provide an instance of UserRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(Looper.getMainLooper()).postDelayed({
        splashViewModel.isUserLogged()
        }, 1000) // 2000 milliseconds (3 seconds) delay

        splashViewModel.loginStatus.observe(this) { isLoggedIn ->

            val intent = if (isLoggedIn) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }

            startActivity(intent)
            finish()
        }

    }

}
