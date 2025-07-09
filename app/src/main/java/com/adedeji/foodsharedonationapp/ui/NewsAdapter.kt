package com.adedeji.foodsharedonationapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedeji.foodsharedonationapp.data.model.News
import com.adedeji.foodsharedonationapp.databinding.ItemFoodshareWorksBinding

class NewsAdapter(private var news: List<News>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun getItemCount() = news.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemFoodshareWorksBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(news[position])
    }


    inner class ViewHolder(private val binding: ItemFoodshareWorksBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.newsTitle.text = news.title
            binding.newsDescriptionText.text = news.description
            binding.imageViewItem.setImageResource(news.imageResId)
        }
    }

    fun updateData(newNews: List<News>) {
        news = newNews
        notifyDataSetChanged()
    }
}