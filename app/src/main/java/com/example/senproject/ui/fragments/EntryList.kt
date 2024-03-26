package com.example.senproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.senproject.R
import com.example.senproject.databinding.EntryListBinding
import com.example.senproject.ui.adapters.EntryListAdapter
import com.example.senproject.ui.viewmodels.EntryListViewModel

class EntryList : Fragment() {
    private var binding: EntryListBinding? = null

    private lateinit var entryListViewModel: EntryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        entryListViewModel = ViewModelProvider(this).get(EntryListViewModel::class.java)

        binding = EntryListBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = EntryListAdapter { onEntryClick() }
        binding?.rvEntryList?.adapter = adapter

        entryListViewModel.getAllMoodEntries.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun onEntryClick() {
        findNavController().navigate(R.id.action_entryList_to_entryDescription)
    }
}