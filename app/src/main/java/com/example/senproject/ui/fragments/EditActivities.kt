package com.example.senproject.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
            binding.rvActivities.adapter = adapter
        }

        binding = FragmentEditActivitiesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cvAddEdit.visibility = View.GONE
        binding.cvEmoji.visibility = View.GONE
        initButtons()
    }

    private fun initButtons() {
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.cvAddButton.setOnClickListener { onClickAdd() }
        binding.btnConfirm.setOnClickListener { onClickConfirm() }
        binding.btnCancel.setOnClickListener { onClickCancel() }
        binding.ivIcon.setOnClickListener {
            if (binding.cvEmoji.visibility == View.GONE) {
                binding.cvEmoji.visibility = View.VISIBLE
                binding.cvActivitiesList.visibility = View.GONE
                binding.cvAddButton.visibility = View.GONE
            } else {
                binding.cvEmoji.visibility = View.GONE
                binding.cvActivitiesList.visibility = View.VISIBLE
                binding.cvAddButton.visibility = View.VISIBLE
            }
        }
        binding.emojiPicker.setOnEmojiPickedListener{
            binding.ivIcon.text = it.emoji
        }
    }

    private fun onClickAdd() {
        binding.cvAddEdit.visibility = View.VISIBLE
        currentActivity = null
        binding.etName.text = null
    }

    private fun onClickCancel() {
        binding.cvActivitiesList.visibility = View.VISIBLE
        binding.cvAddButton.visibility = View.VISIBLE
        binding.cvAddEdit.visibility = View.GONE
        binding.cvEmoji.visibility = View.GONE
        currentActivity = null
        binding.etName.text = null
    }

    private fun onClickConfirm() {
        //ToDo: hide softKeyboard
        binding.cvActivitiesList.visibility = View.VISIBLE
        binding.cvAddButton.visibility = View.VISIBLE
        binding.cvAddEdit.visibility = View.GONE
        binding.cvEmoji.visibility = View.GONE
        if(currentActivity == null){
            val name = binding.etName.text.toString()
            if (name.isNotEmpty()) {
                viewModel.addActivities(ActivitiesCheck(id = 0, name = name, iconEmoji = binding.ivIcon.text.toString()))
            }
        } else {
            val name = binding.etName.text.toString()
            if (name.isNotEmpty()) {
                val newActivity = ActivitiesCheck(id = currentActivity!!.id, name = name, iconEmoji = binding.ivIcon.text.toString())
                viewModel.updateActivities(newActivity)
            }
        }
    }

    private fun onClick(a: ActivitiesCheck) {
        currentActivity = a
        binding.cvAddEdit.visibility = View.VISIBLE
        binding.etName.setText(a.name)
    }

    private fun onDeleteClick(a: ActivitiesCheck) {
        val iDialogue = DialogInterface.OnClickListener { dialogInterface, i ->
            when (i) {
                DialogInterface.BUTTON_POSITIVE -> viewModel.deleteActivities(a)
            }
        }
        val bDialog = AlertDialog.Builder(context)
        bDialog
            .setMessage("Are you sure you want to delete the activity:\n" + a.name)
            .setPositiveButton("Yes", iDialogue)
            .setNegativeButton("No", iDialogue)
            .show()
    }
}