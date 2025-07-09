package com.adedeji.foodsharedonationapp.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adedeji.foodsharedonationapp.R
import com.adedeji.foodsharedonationapp.data.model.Donation
import com.adedeji.foodsharedonationapp.databinding.ActivityHomeBinding
import com.adedeji.foodsharedonationapp.databinding.DialogAddNewDonationBinding
import com.adedeji.foodsharedonationapp.databinding.DialogDonationSuccessBinding
import com.adedeji.foodsharedonationapp.databinding.DialogLogoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.getValue

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        //for bottom navigation
        binding.bottomNavBar.setOnNavigationItemSelectedListener(this)

        //to inflate recycler view from the database
        loadData()

        binding.donateNowButton.setOnClickListener {
            showDonationDialog()
        }

        // For Logout
        binding.iconUserLogout.setOnClickListener {
            Log.d("Logout", "Logout button clicked")
            logout()
        }
    }

    private fun logout() {
        val dialogBinding = DialogLogoutBinding.inflate(LayoutInflater.from(this))
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        // Get the logged-in user's email from shared preferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("logged_in_user_email", null)

        // Set default text while fetching username
        dialogBinding.logoutText.text = "Hello User, Do you want to logout?"

        // Fetch username from database using email
        lifecycleScope.launch {
            if (userEmail != null) {
                val user = viewModel.getUserByEmail(userEmail)
                user?.let {
                    dialogBinding.logoutText.text = "Hello ${it.userName}, Do you want to logout?"
                }
            }
        }

        //yes button
        dialogBinding.btnYes.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //no button
        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setupRecyclerView() {
        binding.recentWorksRecycler.layoutManager = LinearLayoutManager(this)
        binding.recentWorksRecycler.adapter = NewsAdapter(emptyList())
    }

    //setting up bottom Navigation
    override fun onNavigationItemSelected(item: MenuItem): Boolean  {
        when (item.itemId) {
            R.id.nav_home -> {
                // Already on home
                true
            }
            R.id.nav_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> false
        }
        return true
    }

    //Load news data from the database using viewModel
    private fun loadData() {
        lifecycleScope.launch {
            viewModel.insertDummyNews()
            val news = viewModel.getAllNews() ?: emptyList()
            (binding.recentWorksRecycler.adapter as? NewsAdapter)?.updateData(news)
        }
    }

    //Showing the Bottom dialog to add a new donation
    private fun showDonationDialog() {
        val dialogBinding = DialogAddNewDonationBinding.inflate(LayoutInflater.from(this))
        val bottomDialog = BottomSheetDialog(this)
        bottomDialog.setContentView(dialogBinding.root)

        // Setup spinner
        val methods = arrayOf("Pickup", "Delivery", "Drop-off")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, methods)
        dialogBinding.spinnerDonationMethod.adapter = adapter

        // Date picker
        dialogBinding.editTextExpiryDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(Calendar.YEAR, selectedYear)
                    calendar.set(Calendar.MONTH, selectedMonth)
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay)

                    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    dialogBinding.editTextExpiryDate.setText(dateFormat.format(calendar.time))
                },
                year, month, day
            )

            // Set minimum date to today
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        // Save button
        dialogBinding.buttonSaveDonation.setOnClickListener {
            val foodType = dialogBinding.editTextFoodType.text.toString()
            val quantity = dialogBinding.editTextFoodQuantity.text.toString().toIntOrNull() ?: 0
            val expiryDate = dialogBinding.editTextExpiryDate.text.toString()
            val method = dialogBinding.spinnerDonationMethod.selectedItem.toString()

            if (validateInput(foodType, quantity, expiryDate)) {
                val donation = Donation(
                    foodType = foodType,
                    quantity = quantity,
                    expiryDate = expiryDate,
                    donationMethod = method
                )
                lifecycleScope.launch {
                    viewModel.insertDonation(donation)
                    bottomDialog.dismiss()
                    showSuccessDialog()
                }
            }
        }

        bottomDialog.show()
    }

    private fun validateInput(foodType: String, quantity: Int, expiryDate: String): Boolean {
        return foodType.isNotEmpty() && quantity > 0 && expiryDate.isNotEmpty()
    }

    //Showing the success bottom dialog
    private fun showSuccessDialog() {
        val dialogBinding = DialogDonationSuccessBinding.inflate(LayoutInflater.from(this))
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)

        dialogBinding.btnHome.setOnClickListener { dialog.dismiss() }
        dialogBinding.btnHistory.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        dialog.show()
    }

}