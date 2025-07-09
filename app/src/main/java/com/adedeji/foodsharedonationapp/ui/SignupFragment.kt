package com.adedeji.foodsharedonationapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adedeji.foodsharedonationapp.R
import com.adedeji.foodsharedonationapp.databinding.FragmentSignupBinding
import com.adedeji.foodsharedonationapp.utils.HashUtils
import kotlinx.coroutines.launch
import kotlin.getValue


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        //validate user input for signing up new account
        binding.buttonSignup.setOnClickListener {
            val email = binding.editTextSignupEmail.text.toString().trim()
            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextSignupPassword.text.toString().trim()
            val confirmPassword = binding.editTextSignupConfirmPassword.text.toString().trim()
            val termsChecked = binding.checkboxTerms.isChecked

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all fields and check the terms",
                    Toast.LENGTH_LONG
                ).show()
            } else if (password != confirmPassword) {
                Toast.makeText(
                    requireContext(),
                    "Passwords do not match",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (!termsChecked) {
                Toast.makeText(
                    requireContext(),
                    "Please check the terms",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                signUpUser(email, username, password)
            }
        }

        //click listener for switching to the login view
        binding.textViewLoginLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_content, LoginFragment())
                .commit()
        }

        return binding.root
    }

    private fun signUpUser(email: String, username: String, password: String) {
        //setup after database is set up
        lifecycleScope.launch{
            //check if a user with the same email already exists
            val user = viewModel.getUser(email)

            if (user == null){
                val hashedPassword = HashUtils.sha256(password)
                //create user
                viewModel.createUser(email, hashedPassword, username)

                Toast.makeText(requireContext(), "User created successfully", Toast.LENGTH_LONG).show()
                //after login switch to login screen
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_content, LoginFragment())
                    .commit()
            } else {
                Toast.makeText(requireContext(), "User already exists", Toast.LENGTH_LONG).show()
            }



        }

    }
}