package com.example.senproject

import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.senproject.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.container)
        setupWithNavController(binding.botNav, navController)

        navController.addOnDestinationChangedListener{ _: NavController, navDestination: NavDestination, _: Bundle? ->
            if(navDestination.label == "create_entry"){
                binding.botNav.visibility = View.GONE
            } else {
                binding.botNav.visibility = View.VISIBLE
            }
        }
    }
}