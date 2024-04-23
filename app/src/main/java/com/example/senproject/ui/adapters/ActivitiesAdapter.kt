package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.databinding.ActivitiesItemBinding

class ActivitiesAdapter (
    private val list: List<ActivitiesCheck>,
    private val editMode: Boolean,
    private val onDeleteClick: ((a: ActivitiesCheck) -> Unit)? = null,
    private val onClick: ((a: ActivitiesCheck) -> Unit)? = null
    ): RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>(){

    class ViewHolder(
        private val activitiesItemBinding: ActivitiesItemBinding,
        private val editMode: Boolean,
        private val onDeleteClick: ((a: ActivitiesCheck) -> Unit)? = null,
        private val onClick: ((a: ActivitiesCheck) -> Unit)? = null
    ): RecyclerView.ViewHolder(activitiesItemBinding.root){
        fun bindItem(activitiesCheck: ActivitiesCheck) {
            activitiesItemBinding.tvActivities.text = activitiesCheck.name

            if(editMode) {
                activitiesItemBinding.cbActivities.visibility = View.GONE
                activitiesItemBinding.delete.visibility = View.VISIBLE

                if (onDeleteClick == null) throw IllegalArgumentException("Null onDeleteClick listener")
                else activitiesItemBinding.delete.setOnClickListener{onDeleteClick}

                if (onClick == null) throw IllegalArgumentException("Null onClick listener")
                else itemView.setOnClickListener{onClick}

            } else {
                activitiesItemBinding.delete.visibility = View.GONE
                activitiesItemBinding.cbActivities.visibility = View.VISIBLE

                activitiesItemBinding.cbActivities.isActivated = activitiesCheck.checked
                activitiesItemBinding.cbActivities.setOnClickListener {
                    itemView.isActivated = !itemView.isActivated
                    activitiesCheck.checked = !activitiesCheck.checked
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (onDeleteClick != null) return ViewHolder(
            ActivitiesItemBinding.inflate(LayoutInflater.from(parent.context)),
            editMode,
            onDeleteClick)

        return ViewHolder(
            ActivitiesItemBinding.inflate(LayoutInflater.from(parent.context)),
            editMode)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }
}