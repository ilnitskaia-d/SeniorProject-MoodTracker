package com.example.senproject.ui.fragments

import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.senproject.R
import com.github.mikephil.charting.components.MarkerView

class ScatterMarkerView(context: Context?, layoutRes: Int) :
MarkerView(context, layoutRes){
    val moodEntryLabel: TextView = findViewById<View>(R.id.scatter_marker_label) as TextView

    fun getXOffset(xpos: Float): Int = -(width / 2)
    fun getYOffset(ypos: Float): Int = -height

}