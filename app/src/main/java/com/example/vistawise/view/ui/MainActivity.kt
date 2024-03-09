package com.example.vistawise.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vistawise.databinding.ActivityMainBinding
import com.example.vistawise.viewmodel.MainResult
import com.example.vistawise.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import view.adapter.DestinationsAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivityViewModel.getUser()

        // Set up the RecyclerView
        binding.rvDestination.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mainActivityViewModel.mainResult.observe(this) { mainResult ->
            handleScreenState(mainResult)
        }

        mainActivityViewModel.getTopDestinations()

    }

    private fun handleScreenState(mainResult: MainResult) {
        when (mainResult) {
            is MainResult.Loading -> {
                loading(true)
            }

            is MainResult.UserSuccess -> {
                binding.userDetails.text = "Hello, ${mainResult.userName}"
            }

            is MainResult.UserError -> {
                Toast.makeText(this, mainResult.userError.message, Toast.LENGTH_SHORT).show()
                goToLogin()
            }

            is MainResult.DestinationSuccess -> {
                loading(false)
                val adapter = DestinationsAdapter(mainResult.destinations)
                binding.rvDestination.adapter = adapter
            }

            is MainResult.DestinationError -> {
                loading(false)
                Toast.makeText(this, mainResult.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvDestination.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.rvDestination.visibility = View.VISIBLE
        }
    }
}