package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.databinding.ActivitiesItemBinding

class ActivitiesEntryAdapter (private val list: List<ActivitiesCheck>):
    RecyclerView.Adapter<ActivitiesEntryAdapter.ViewHolder>(){

    class ViewHolder(private val activitiesItemBinding: ActivitiesItemBinding):
        RecyclerView.ViewHolder(activitiesItemBinding.root){
        fun bindItem(activitiesCheck: ActivitiesCheck) {
            activitiesItemBinding.cbActivities.isActivated = activitiesCheck.checked!!
            activitiesItemBinding.cbActivities.setOnClickListener {
                itemView.isActivated = !itemView.isActivated
            }
            activitiesItemBinding.tvActivities.text = activitiesCheck.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivitiesItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindItem(item)
    }
}