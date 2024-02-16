package com.example.senproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.senproject.R
import com.example.senproject.database.MoodEntry
import com.example.senproject.database.MoodState
import com.example.senproject.databinding.EntryListBinding
import com.example.senproject.ui.adapter.EntryListAdapter

class EntryList : Fragment() {
    val list = listOf<MoodEntry>(
        MoodEntry(moodState = MoodState.GOOD, time = "1:00"),
        MoodEntry(moodState = MoodState.GOOD, time = "2:00"),
        MoodEntry(moodState = MoodState.OK, time = "3:00"),
        MoodEntry(moodState = MoodState.BAD, time = "4:00")
    )

    var binding: EntryListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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