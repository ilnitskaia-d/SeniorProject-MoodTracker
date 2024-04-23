package com.example.senproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.databinding.FragmentEditActivitiesBinding
import com.example.senproject.ui.adapters.ActivitiesAdapter
import com.example.senproject.ui.viewmodels.EditActivitiesViewModel


class EditActivities : Fragment() {
    private lateinit var binding: FragmentEditActivitiesBinding
    private lateinit var viewModel: EditActivitiesViewModel
    private lateinit var adapter: ActivitiesAdapter

    private var currentActivity: ActivitiesCheck? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[EditActivitiesViewModel::class.java]
        viewModel.getAllActivities.observe(viewLifecycleOwner) {
            adapter = ActivitiesAdapter(it, true, this::onDeleteClick, this::onClick)
        }

        binding = FragmentEditActivitiesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cvAddEdit.visibility = View.GONE
        binding.btnCancel.setOnClickListener { onClickConfirm() }
        binding.btnCancel.setOnClickListener { onClickCancel() }
    }

    private fun onClickCancel() {
        //ToDo: Cancel
        binding.cvAddEdit.visibility = View.GONE
        currentActivity = null
        //Reset Recycler View View?
    }

    private fun onClickConfirm() {
        //ToDo: Confirm
        binding.cvAddEdit.visibility = View.GONE
        if(currentActivity == null){
            //Add new
        } else {
            //update old
        }
    }

    private fun onClick(a: ActivitiesCheck) {
        currentActivity = a
        binding.cvAddEdit.visibility = View.VISIBLE
        binding.tvName.text = a.name
    }

    private fun onDeleteClick(a: ActivitiesCheck) {
        viewModel.deleteActivities(a)
    }
}