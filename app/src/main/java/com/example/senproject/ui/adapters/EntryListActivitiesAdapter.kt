package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.databinding.EntryListActivityItemBinding

class EntryListActivitiesAdapter(private var list: List<String>):
    RecyclerView.Adapter<EntryListActivitiesAdapter.ViewHolder>() {

    class ViewHolder(private var binding: EntryListActivityItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val textView = binding.tvName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(EntryListActivityItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
