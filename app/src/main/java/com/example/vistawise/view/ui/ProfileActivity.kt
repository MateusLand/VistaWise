package com.example.vistawise.com.example.vistawise//package view.ui
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import com.example.vistawise.databinding.ActivityProfileBinding
//import com.example.vistawise.viewmodel.ProfileViewModel
//
//class ProfileActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityProfileBinding
//    private lateinit var profileViewModel: ProfileViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityProfileBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
//
//        binding.apply {
//            btnEditProfile.setOnClickListener {
//                // Navigate to the EditProfileActivity or implement the edit profile functionality???
//            }
//
//            btnLogout.setOnClickListener {
//                profileViewModel.logout()
//            }
//        }
//
//        observeViewModel()
//    }
//
//    private fun observeViewModel() {
//        profileViewModel.currentUser.observe(this) { currentUser ->
//            if (currentUser == null) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            } else {
//                // Fetch and display user details
//                binding.apply {
//                    tvProfileName.text = currentUser.displayName
//                    tvProfileEmail.text = currentUser.email
//                }
//            }
//        }
//
//        profileViewModel.errorMessage.observe(this) { errorMessage ->
//            if (errorMessage.isNotEmpty()) {
//                // Show error message
//            }
//        }
//    }
//}
