package com.example.senproject.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.senproject.R
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.FragmentEntryListBinding
import com.example.senproject.ui.adapters.EntryListAdapter
import com.example.senproject.ui.fragments.EntryListDirections.ActionEntryListToEntryDescription
import com.example.senproject.ui.viewmodels.EntryListViewModel
import com.example.senproject.utils.Utilities.getTodayDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class EntryList : Fragment() {
    private lateinit var binding: FragmentEntryListBinding
    private lateinit var entryListViewModel: EntryListViewModel
    private lateinit var adapter: EntryListAdapter
    private lateinit var today: LocalDate
    private var calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        entryListViewModel = ViewModelProvider(this).get(EntryListViewModel::class.java)
        today = getTodayDate()
        binding = FragmentEntryListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = EntryListAdapter(::onEntryClick)
        binding.rvEntryList.adapter = adapter

        binding.tvDate.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.tvDate.setOnClickListener {
            showDatePicker()
        }

        entryListViewModel.getMoodEntriesByDate(today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        subscribeToLiveData()
        binding.tvDate.text = "Today"
    }

    private fun onEntryClick(moodEntry: MoodEntry) {
//        val f = EntryDescription()
//        f.arguments =  bundleOf("argEntry" to moodEntry)
//        parentFragmentManager.beginTransaction()
//            .add(R.id.container, f, null).commit()

        findNavController().navigate(EntryListDirections.actionEntryListToEntryDescription(moodEntry))
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(requireContext(), { DatePicker, year: Int, month: Int, day: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)
            val formattedDate = SimpleDateFormat("dd.MM.yy").format(selectedDate.time)

            if(formattedDate == today.format(DateTimeFormatter.ofPattern("dd.MM.yy"))) binding.tvDate.text = "Today"
            else binding.tvDate.text = formattedDate

            entryListViewModel.getMoodEntriesByDate(SimpleDateFormat("yyyy-MM-dd").format(selectedDate.time))
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