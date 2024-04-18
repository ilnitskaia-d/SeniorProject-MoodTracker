package com.example.senproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.senproject.R
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.EntryDescriptionBinding
import com.example.senproject.ui.adapters.EntryDescriptionActivitiesAdapter
import com.example.senproject.utils.Utilities
import java.io.Serializable
import java.time.format.DateTimeFormatter

class EntryDescription : Fragment() {
    private lateinit var binding: EntryDescriptionBinding
    private val args: EntryDescriptionArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EntryDescriptionBinding.inflate(layoutInflater)
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
                findNavController().navigateUp()
            }
        }
    }
}