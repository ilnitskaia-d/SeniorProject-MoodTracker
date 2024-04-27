package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.R
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.data.MoodState
import com.example.senproject.databinding.EntryItemBinding
import com.example.senproject.utils.Utilities

class EntryListAdapter(private val onClick:(moodEntry: MoodEntry) -> Unit)
    : RecyclerView.Adapter<EntryListAdapter.ViewHolder>()
{
    var list: List<MoodEntry> = emptyList()

    class ViewHolder(private val entryItemBinding: EntryItemBinding, private val onClick:(moodEntry: MoodEntry) -> Unit):
        RecyclerView.ViewHolder(entryItemBinding.root) {

        fun bindItem(moodEntry: MoodEntry) {

            itemView.setOnClickListener {
                onClick(moodEntry)
            }

            entryItemBinding.apply {
                moodEntry.apply {
                    ivMood.setImageResource(Utilities.getMoodIcon(moodState))
                    tvTime.text = time
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(EntryItemBinding.inflate(LayoutInflater.from(parent.context)), onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    fun setData(entryList: List<MoodEntry>?) {
        if (entryList != null) {
            this.list = entryList
            notifyDataSetChanged()
        }
    }
}