package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.R
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.databinding.ActivitiesItemBinding

var SelectedPosition: Int? = null

class ActivitiesAdapter (
    var list: List<ActivitiesCheck>,
    private val editMode: Boolean,
    private val onDeleteClick: ((a: ActivitiesCheck) -> Unit)? = null,
    private val onClick: ((a: ActivitiesCheck) -> Unit)? = null
    ): RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>(){

    inner class ViewHolder(
        private val activitiesItemBinding: ActivitiesItemBinding,
        private val editMode: Boolean,
        private val onDeleteClick: ((a: ActivitiesCheck) -> Unit)? = null,
        private val onClick: ((a: ActivitiesCheck) -> Unit)? = null
    ): RecyclerView.ViewHolder(activitiesItemBinding.root){

        fun bindItem(activitiesCheck: ActivitiesCheck) {
            activitiesItemBinding.tvActivities.text = activitiesCheck.name
            activitiesItemBinding.icon.text = activitiesCheck.iconEmoji
            if(editMode) {
                activitiesItemBinding.cbActivities.visibility = View.GONE
                activitiesItemBinding.delete.visibility = View.VISIBLE

                if (onDeleteClick == null) throw IllegalArgumentException("Null onDeleteClick listener")
                else activitiesItemBinding.delete.setOnClickListener{ onDeleteClick?.let { it1 -> it1(activitiesCheck) } }

                itemView.setOnClickListener {
                    onClick?.let { it1 -> it1(activitiesCheck) }
                    setSelection(layoutPosition)
                }

                if (layoutPosition == SelectedPosition) itemView.setBackgroundResource(R.color.secondary_background)
                else itemView.setBackgroundResource(com.google.android.material.R.color.mtrl_btn_transparent_bg_color)

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

    fun setSelection(layoutPosition: Int) {
        if (SelectedPosition == null) return

        notifyItemChanged(SelectedPosition!!)
        SelectedPosition = layoutPosition
        notifyItemChanged(SelectedPosition!!)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (editMode) return ViewHolder(
            ActivitiesItemBinding.inflate(LayoutInflater.from(parent.context)),
            editMode,
            onDeleteClick, onClick)

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

    fun setData(NewList: List<ActivitiesCheck>?) {
        if (NewList != null) {
            this.list = NewList
            notifyDataSetChanged()
        }
    }
}