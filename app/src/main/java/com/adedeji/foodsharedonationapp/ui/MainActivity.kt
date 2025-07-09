package com.adedeji.foodsharedonationapp.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adedeji.foodsharedonationapp.R
import com.adedeji.foodsharedonationapp.databinding.ActivityMainBinding
import com.adedeji.foodsharedonationapp.databinding.DialogLoginScreenBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconUserAccount.setOnClickListener {
            loginDialog()
        }
    }

    private fun loginDialog() {
        val dialog = Dialog(this)
        val dialogBinding = DialogLoginScreenBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        // Handle Login button click
        dialogBinding.btnLogin.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_content, LoginFragment())
                .commit()
            dialog.dismiss()
        }

        // Handle Signup button click
        dialogBinding.btnSignup.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_content, SignupFragment())
                .commit()
            dialog.dismiss()
        }

        dialog.show()
    }
}