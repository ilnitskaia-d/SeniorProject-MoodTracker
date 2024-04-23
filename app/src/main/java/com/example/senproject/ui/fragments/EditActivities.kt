package com.example.senproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.senproject.R
import com.example.senproject.databinding.FragmentEditActivitiesBinding


class EditActivities : Fragment() {
    private lateinit var binding: FragmentEditActivitiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditActivitiesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}