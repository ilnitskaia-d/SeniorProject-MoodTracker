package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.R
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.data.MoodState
import com.example.senproject.databinding.EntryItemBinding

class EntryListAdapter: RecyclerView.Adapter<EntryListAdapter.ViewHolder>()
{
    var list: List<MoodEntry> = emptyList<MoodEntry>()

    class ViewHolder(private val entryItemBinding: EntryItemBinding):
        RecyclerView.ViewHolder(entryItemBinding.root) {
        fun bindItem(moodEntry: MoodEntry) {
            if(moodEntry.moodState == MoodState.GOOD) {
                entryItemBinding.ivMood.setImageResource(R.drawable.ic_face)
            } else {
                entryItemBinding.ivMood.setImageResource(R.drawable.ic_face2)
            }

            entryItemBinding.tvTime.text = moodEntry.time
            //toDo fun for setting the activities and emotions

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(EntryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = list[position]
        holder.bindItem(entry)
    }

    fun setData(entryList: List<MoodEntry>) {
        this.list = entryList
        notifyDataSetChanged()
    }
}