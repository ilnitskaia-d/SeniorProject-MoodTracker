package com.example.senproject.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.senproject.R
import com.example.senproject.data.MoodState
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.ActivityMainBinding
import com.example.senproject.ui.fragments.EntryListDirections
import com.example.senproject.ui.viewmodels.CreateEntryViewModel
import com.example.senproject.ui.viewmodels.EditActivitiesViewModel
import com.example.senproject.utils.Utilities.getTimeNow
import com.example.senproject.utils.Utilities.getTodayDate


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.ivWelcome.visibility = View.VISIBLE
        setContentView(binding.root)

        binding.ivWelcome.postDelayed(
            Runnable { kotlin.run {
                binding.ivWelcome.visibility = View.GONE
            } },
            1000
        )

        val pref = this.getSharedPreferences("PREFS", 0)
        val editor = pref.edit()
        val firstRun = pref.getBoolean("firstRun", true)
        if (firstRun) {
            val activitiesViewModel = ViewModelProvider(this)[EditActivitiesViewModel::class.java]

            val list = listOf(
                ActivitiesCheck(id = 0,name = "Food" ),
                ActivitiesCheck(id = 0,name = "Study" ),
                ActivitiesCheck(id = 0,name = "Workout" ),
                ActivitiesCheck(id = 0,name = "Sleep" ),
                ActivitiesCheck(id = 0,name = "Socialize" ),
                ActivitiesCheck(id = 0,name = "Cleaning" )
                )

            for (ac in list) activitiesViewModel.addActivities(ac)


            val createEntryViewModel = ViewModelProvider(this)[CreateEntryViewModel::class.java]
            createEntryViewModel.deleteAll()

            val today = getTodayDate()

            val entriesList = listOf(
                MoodEntry(0, MoodState.OK, getTimeNow(), today, emptyList<String>(), ""),
                MoodEntry(0, MoodState.GOOD, getTimeNow(), today.minusDays(1), emptyList<String>(), ""),
                MoodEntry(0, MoodState.V_BAD, getTimeNow(), today.minusDays(2), emptyList<String>(), ""),
                MoodEntry(0, MoodState.BAD, getTimeNow(), today.minusDays(3), emptyList<String>(), ""),
                MoodEntry(0, MoodState.V_GOOD, getTimeNow(), today.minusDays(4), emptyList<String>(), ""),
                MoodEntry(0, MoodState.OK, getTimeNow(), today.minusDays(5), emptyList<String>(), ""),
                MoodEntry(0, MoodState.OK, getTimeNow(), today.minusDays(6), emptyList<String>(), ""),
            )

            for(e in entriesList) createEntryViewModel.addMoodEntry(e)

            editor.putBoolean("firstRun", false)
            editor.apply()
        }

        navController = Navigation.findNavController(this, R.id.container)
        setupWithNavController(binding.botNav, navController)

        //toDo add control on the upper bar with "back" btn
        navController.addOnDestinationChangedListener{ _: NavController, navDestination: NavDestination, _: Bundle? ->
            if(navDestination.label == "create_entry" ||
                navDestination.label == "edit_activities" ||
                navDestination.label == "edit_activities" ||
                navDestination.label == "chat"){
                binding.botNav.visibility = View.GONE
                binding.btnChat.visibility = View.GONE
            } else {
                binding.botNav.visibility = View.VISIBLE
                binding.btnChat.visibility = View.VISIBLE
            }
        }

        binding.btnChat.setOnClickListener {
            navController.navigate(EntryListDirections.actionEntryListToChat())
        }
    }
}