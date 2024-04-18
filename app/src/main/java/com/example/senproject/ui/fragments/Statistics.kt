package com.example.senproject.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.senproject.R
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.StatisticsBinding
import com.example.senproject.ui.viewmodels.StatisticsViewModel
import com.example.senproject.utils.MoodEntryScatterGraph
import com.example.senproject.utils.Utilities.getMoodIcon
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlin.math.log


class Statistics : Fragment() {
    private lateinit var binding: StatisticsBinding
    private lateinit var statisticsViewModel: StatisticsViewModel
    private lateinit var scatterGraph: MoodEntryScatterGraph
    private lateinit var scatterChart: ScatterChart
    private lateinit var markerView: ScatterMarkerView
    private var moodEntries: List<MoodEntry>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        statisticsViewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]
        binding = StatisticsBinding.inflate(layoutInflater)
        scatterGraph = MoodEntryScatterGraph()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        statisticsViewModel.getAllMoodEntries.observe(viewLifecycleOwner) {
            moodEntries = it
            if (moodEntries != null) {
                plotScatterGraph()
            }
        }
    }

    private fun plotScatterGraph() {
        val xPos = scatterGraph.getX(moodEntries!!)

        val dataSet = ArrayList<Entry>()

        for ((index, entry) in moodEntries!!.withIndex()) {
            dataSet.add(
                Entry(
                    xPos[index],
                    scatterGraph.getY(entry),
                    resources.getDrawable(getMoodIcon(entry.moodState)),
                    ""
                )
            )
        }

        val scatterDataSet = ScatterDataSet(dataSet, "")
        scatterDataSet.setDrawValues(false)

        scatterChart = binding.scatterChart
//        scatterChart.data = ScatterData(scatterDataSet)
        scatterChart.notifyDataSetChanged()
        scatterChart.invalidate()

        scatterChart.axisRight.isEnabled = false
        scatterChart.axisLeft.isEnabled = false
        scatterChart.xAxis.isEnabled = false
        scatterChart.description.text = ""
        scatterChart.legend.isEnabled = false
        scatterChart.setBackgroundColor(activity?.resources!!.getColor(R.color.main_background))

        scatterChart.setTouchEnabled(true)
        scatterChart.setPinchZoom(true)

        scatterChart.setNoDataText("No data to display")

        markerView = ScatterMarkerView(activity, R.layout.scatter_plot)
        scatterChart.setDrawMarkers(true)
        scatterChart.marker = markerView

        scatterChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(
                e: Entry,
                h: Highlight?
            ) {
                scatterChart.highlightValue(h)
                //markerView.moodEntryLabel.text = e.data.toString()
            }

            override fun onNothingSelected() {}
        })

    }
}