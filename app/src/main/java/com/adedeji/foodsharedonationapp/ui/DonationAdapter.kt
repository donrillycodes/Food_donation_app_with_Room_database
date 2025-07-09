package com.adedeji.foodsharedonationapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedeji.foodsharedonationapp.data.model.Donation
import com.adedeji.foodsharedonationapp.databinding.ItemDonationBinding

class DonationAdapter(private var donations: List<Donation>, private val onLongPress: (Donation) -> Unit ) : RecyclerView.Adapter<DonationAdapter.DonationViewHolder>() {

    override fun getItemCount(): Int = donations.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val binding = ItemDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        holder.bind(donations[position])
    }


    fun updateData(newDonations: List<Donation>) {
        donations = newDonations
        notifyDataSetChanged()
    }

    inner class DonationViewHolder(private val binding: ItemDonationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(donation: Donation) {

            binding.root.setOnLongClickListener {
                onLongPress(donations[adapterPosition])
                true
            }

            binding.textFoodType.text = "Name: ${donation.foodType}"
            binding.textFoodQuantity.text = "Qty: ${donation.quantity} items"
            binding.textDonationMethod.text = "Method:${donation.donationMethod}"
            binding.textExpiryDate.text = "Expires: ${donation.expiryDate}"
        }
    }

}
