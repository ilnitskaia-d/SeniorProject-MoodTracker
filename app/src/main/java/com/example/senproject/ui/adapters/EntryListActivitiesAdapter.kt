package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.databinding.EntryListActivityItemBinding

class EntryListActivitiesAdapter (val list: List<String>):
    RecyclerView.Adapter<EntryListActivitiesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: EntryListActivityItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: String) {
            binding.tvName.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(EntryListActivityItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

}