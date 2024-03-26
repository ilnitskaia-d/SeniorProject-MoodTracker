package com.example.senproject.ui.adapters

import android.util.Log
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
            itemView.setOnClickListener {
                //ToDo: The whole entry
            }

            entryItemBinding.apply {
                moodEntry.apply {
                    when (moodState) {
                        MoodState.V_GOOD -> ivMood.setImageResource(R.drawable.emotion_great)
                        MoodState.GOOD -> ivMood.setImageResource(R.drawable.emotion_good)
                        MoodState.OK -> ivMood.setImageResource(R.drawable.emotion_ok)
                        MoodState.BAD -> ivMood.setImageResource(R.drawable.emotion_notgood)
                        MoodState.V_BAD -> ivMood.setImageResource(R.drawable.emotion_bad)
                    }
                }

                tvTime.text = moodEntry.time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(EntryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    fun setData(entryList: List<MoodEntry>) {
        this.list = entryList
        notifyDataSetChanged()
    }
}