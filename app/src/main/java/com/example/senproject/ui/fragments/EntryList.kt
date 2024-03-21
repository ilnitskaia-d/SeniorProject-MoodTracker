package com.example.senproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.data.MoodState
import com.example.senproject.databinding.EntryListBinding
import com.example.senproject.ui.adapters.EntryListAdapter

class EntryList : Fragment() {
    private val list = listOf(
        MoodEntry(id = 0, moodState = MoodState.GOOD, time = "1:00", activities = listOf("")),
        MoodEntry(id = 0, moodState = MoodState.GOOD, time = "2:00", activities = listOf("")),
        MoodEntry(id = 0, moodState = MoodState.OK, time = "3:00", activities = listOf("")),
        MoodEntry(id = 0, moodState = MoodState.BAD, time = "4:00", activities = listOf(""))
    )

    private var binding: EntryListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EntryListBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = EntryListAdapter(list)
        binding?.rvEntryList?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}