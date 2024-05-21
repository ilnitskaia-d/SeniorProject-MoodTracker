package com.example.senproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.FragmentEntryDescriptionBinding
import com.example.senproject.ui.adapters.EntryDescriptionActivitiesAdapter
import com.example.senproject.ui.viewmodels.EditActivitiesViewModel
import com.example.senproject.utils.Utilities
import java.time.format.DateTimeFormatter

class EntryDescription : Fragment() {
    private lateinit var binding: FragmentEntryDescriptionBinding
    private val args: EntryDescriptionArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntryDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButtons()
        val entry = args.argEntry

        binding.apply {
            ivMood.setImageResource(Utilities.getMoodIcon(entry!!.moodState))
            tvDate.text = entry.day.format(DateTimeFormatter.ofPattern("dd.MM"))
            tvTime.text = entry.time
            tvText.text = entry.text

             rvActivities.adapter = EntryDescriptionActivitiesAdapter(entry.activities)
        }
    }

    private fun initButtons() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(EntryDescriptionDirections.actionEntryDescriptionToEntryList())
            }
        }
    }
}