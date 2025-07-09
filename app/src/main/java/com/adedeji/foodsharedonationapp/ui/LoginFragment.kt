package com.adedeji.foodsharedonationapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adedeji.foodsharedonationapp.R
import com.adedeji.foodsharedonationapp.databinding.FragmentLoginBinding
import com.adedeji.foodsharedonationapp.utils.HashUtils
import kotlinx.coroutines.launch
import kotlin.getValue

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val PREFS_NAME = "LoginPrefs"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        loadSavedCredentials()

        //click listener for login button
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextLoginEmail.text.toString().trim()
            val password = binding.editTextLoginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all fields",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                loginUser(email, password)
            }
        }

        //click listener for switching to the signup view
        binding.textViewSignupLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_content, SignupFragment())
                .commit()
        }

        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        //write code to login user
        lifecycleScope.launch {
            val user = viewModel.getUser(email)
            val hashedPassword = HashUtils.sha256(password)

            if (user == null) {
                Toast.makeText(requireContext(), "User does not exist", Toast.LENGTH_LONG).show()
            } else if (user.userPassword == hashedPassword) {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_LONG).show()

                // Store email in user_prefs for session management
                val userPrefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                userPrefs.edit().putString("logged_in_user_email", email).apply()

                // Save credentials to shared preferences
                if (binding.checkboxRememberMe.isChecked) {
                    saveCredentials(email, password)
                } else {
                    clearCredentials()
                }

                //Navigate to home activity
                startActivity(Intent(requireContext(), HomeActivity::class.java))

            } else {
                Toast.makeText(requireContext(), "Incorrect password", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun saveCredentials(email: String, password: String) {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    private fun loadSavedCredentials() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val email = prefs.getString(KEY_EMAIL, null)
        val password = prefs.getString(KEY_PASSWORD, null)
        if (email != null && password != null) {
            binding.editTextLoginEmail.setText(email)
            binding.editTextLoginPassword.setText(password)
            binding.checkboxRememberMe.isChecked = true
        }
    }

    private fun clearCredentials() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            remove(KEY_EMAIL)
            remove(KEY_PASSWORD)
            apply()
        }
    }

}