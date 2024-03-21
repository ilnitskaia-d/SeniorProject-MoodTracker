package com.example.senproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.senproject.R
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.data.MoodState
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.CreateEntryBinding
import com.example.senproject.ui.adapters.ActivitiesEntryAdapter
import com.example.senproject.ui.viewmodels.CreateEntryViewModel
import java.time.LocalDateTime

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

    private var selectedMood: MoodState? = null

    private lateinit var createEntryViewModel: CreateEntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createEntryViewModel = ViewModelProvider(this)[CreateEntryViewModel::class.java]
        activitiesCheckAdapter = ActivitiesEntryAdapter(activities_list)
        binding = CreateEntryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initMoodChoiceTable()

        initButtons()

        binding.rvActivitiesTable.adapter = activitiesCheckAdapter


        binding.btnSave.setOnClickListener {
            addMoodEntryToDB()
        }

        //ToDo: Implement the button for adding and deleting the activities (mb do in the settings fragment?)
    }

    private fun addMoodEntryToDB() {
        if(selectedMood != null ){
            val entry = MoodEntry(
                id = 0,
                moodState = selectedMood!!,
                time = LocalDateTime.now().toString(),
                activities = getCheckedActivities(),
                text = binding.txtInput.toString()
            )

            createEntryViewModel.addMoodEntry(entry)
            Toast.makeText(context, "Entry is saved", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_createEntry_to_entryList)
        } else {
            Toast.makeText(context, "Please select your current mood", Toast.LENGTH_LONG).show()
        }
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

    private fun getCheckedActivities(): List<String> {
        return activities_list
            .filter { activitiesCheck -> activitiesCheck.checked }
            .map { activitiesCheck -> activitiesCheck.name }
    }

    private fun initButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_createEntry_to_entryList)
        }
    }
}