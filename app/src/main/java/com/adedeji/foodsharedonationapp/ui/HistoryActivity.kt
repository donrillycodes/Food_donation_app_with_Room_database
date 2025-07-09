package com.adedeji.foodsharedonationapp.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adedeji.foodsharedonationapp.R
import com.adedeji.foodsharedonationapp.data.model.Donation
import com.adedeji.foodsharedonationapp.databinding.ActivityHistoryBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import kotlin.getValue

class HistoryActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadData()
        //for bottom navigation
        binding.bottomNavBar.setOnNavigationItemSelectedListener(this)

    }

    private fun setupRecyclerView() {
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = DonationAdapter(emptyList()) { donation ->
            // Handle long-press: show confirmation dialog
            showDeleteConfirmationDialog(donation)
        }
    }

    private fun showDeleteConfirmationDialog(donation: Donation) {
        AlertDialog.Builder(this)
            .setTitle("Delete Donation")
            .setMessage("Are you sure you want to delete this donation of ${donation.foodType}?")
            .setPositiveButton("Yes") { _, _ ->
                // Delete donation and refresh data
                lifecycleScope.launch {
                    try {
                        viewModel.deleteDonation(donation)
                        loadData() // Refresh the RecyclerView
                    } catch (e: Exception) {
                        android.widget.Toast.makeText(
                            this@HistoryActivity,
                            "Error deleting donation",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .setNegativeButton("No", null)
            .show()
    }


    private fun loadData() {
        lifecycleScope.launch {
            val donations = viewModel.getAllDonations()
            if (donations.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.historyRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.historyRecyclerView.visibility = View.VISIBLE
                (binding.historyRecyclerView.adapter as DonationAdapter).updateData(donations)
            }
        }
    }

    //setting up bottom Navigation
    override fun onNavigationItemSelected(item: MenuItem): Boolean  {
        when (item.itemId) {
            R.id.nav_home -> {
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
            R.id.nav_history -> {
                true
            }
            else -> false
        }
        return false
    }

}