package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.databinding.ActivitiesCardItemBinding

class EntryDescriptionActivitiesAdapter(private val list: List<String>): RecyclerView.Adapter<EntryDescriptionActivitiesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ActivitiesCardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: String) {
            binding.tvName.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivitiesCardItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }
}