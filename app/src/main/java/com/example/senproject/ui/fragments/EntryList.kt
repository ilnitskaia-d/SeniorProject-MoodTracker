package com.example.senproject.ui.fragments

import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.EntryListBinding
import com.example.senproject.ui.adapters.EntryListAdapter
import com.example.senproject.ui.fragments.EntryListDirections.ActionEntryListToEntryDescription
import com.example.senproject.ui.viewmodels.EntryListViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EntryList : Fragment() {
    private lateinit var binding: EntryListBinding
    private lateinit var entryListViewModel: EntryListViewModel
    private lateinit var adapter: EntryListAdapter
    private var calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        entryListViewModel = ViewModelProvider(this).get(EntryListViewModel::class.java)

        binding = EntryListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = EntryListAdapter(::onEntryClick)
        binding.rvEntryList.adapter = adapter

        binding.tvDate.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.tvDate.setOnClickListener {
            showDatePicker()
        }

        entryListViewModel.getAllMoodEntries.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    private fun onEntryClick(moodEntry: MoodEntry) {
        findNavController().navigate(EntryListDirections.actionEntryListToEntryDescription(moodEntry))
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(requireContext(), { DatePicker, year: Int, month: Int, day: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)
            val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)

            binding.tvDate.text = formattedDate

            entryListViewModel.getMoodEntriesByDate(formattedDate)
            subscribeToLiveData()
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun subscribeToLiveData() {
        entryListViewModel.moodEntriesByDate.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }
}