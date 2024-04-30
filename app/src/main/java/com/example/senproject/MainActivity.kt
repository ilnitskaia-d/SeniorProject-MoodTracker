package com.example.senproject

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.databinding.ActivityMainBinding
import com.example.senproject.ui.viewmodels.EditActivitiesViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            for (ac in list) {
                activitiesViewModel.addActivities(ac)
            }

            editor.putBoolean("firstRun", false)
            editor.apply();
        }

        navController = Navigation.findNavController(this, R.id.container)
        setupWithNavController(binding.botNav, navController)

        navController.addOnDestinationChangedListener{ _: NavController, navDestination: NavDestination, _: Bundle? ->
            if(navDestination.label == "create_entry" ||
                navDestination.label == "edit_activities"){
                binding.botNav.visibility = View.GONE
            } else {
                binding.botNav.visibility = View.VISIBLE
            }
        }
    }
}