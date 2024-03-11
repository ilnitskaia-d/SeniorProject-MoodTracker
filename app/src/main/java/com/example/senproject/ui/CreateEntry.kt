package com.example.senproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.senproject.R
import com.example.senproject.database.ActivitiesCheck
import com.example.senproject.database.MoodEntry
import com.example.senproject.database.MoodState
import com.example.senproject.databinding.CreateEntryBinding
import com.example.senproject.ui.adapter.ActivitiesEntryAdapter

class CreateEntry : Fragment() {

    private lateinit var binding: CreateEntryBinding
    private lateinit var activitiesCheckAdapter: ActivitiesEntryAdapter

    private var activities_list: List<ActivitiesCheck> = listOf(
        ActivitiesCheck(name = "Eat"),
        ActivitiesCheck(name = "Sleep"),
        ActivitiesCheck(name = "Socialization"),
        ActivitiesCheck(name = "Study"),
        ActivitiesCheck(name = "Workout"),
    )

    private lateinit var selectedMood: MoodState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activitiesCheckAdapter = ActivitiesEntryAdapter(activities_list)

        binding = CreateEntryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoodChoiceTable()
        initButtons()
        binding.rvActivitiesTable.adapter = activitiesCheckAdapter

        //ToDo: Implement the button for adding and deleting the activities

        //ToDo: Implement the button for save the entry
    }

    private fun initMoodChoiceTable() {
        val btn_bad = binding.ivBad
        val btn_notgood = binding.ivNotgood
        val btn_ok = binding.ivOk
        val btn_good = binding.ivGood
        val btn_great = binding.ivGreat

        val btnCollection = listOf(btn_bad, btn_notgood, btn_ok, btn_good, btn_great)

        val selectedColor = resources.getColor(R.color.btn_selected)
        val unselectedColor = resources.getColor(R.color.btn_unselected)

        for ((index, button) in btnCollection.withIndex()) {
            button.setOnClickListener { view ->
                for (btn in btnCollection) btn.setBackgroundColor(unselectedColor)

                view.setBackgroundColor(selectedColor)
                selectedMood = MoodState.values()[index]
            }
        }
    }

    private fun initButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_createEntry_to_entryList)
        }
    }
}