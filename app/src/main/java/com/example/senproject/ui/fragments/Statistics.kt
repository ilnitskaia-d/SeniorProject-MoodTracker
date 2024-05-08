package com.example.senproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.senproject.R
import com.example.senproject.data.MoodState
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.FragmentStatisticsBinding
import com.example.senproject.ui.viewmodels.StatisticsViewModel
import com.example.senproject.utils.MoodEntryScatterGraph
import com.example.senproject.utils.Utilities
import com.example.senproject.utils.Utilities.getMoodIcon
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.time.LocalDate


class Statistics : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
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
        binding = FragmentStatisticsBinding.inflate(layoutInflater)
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
    fun getLastWeekEntries(list: List<MoodEntry>) : List<Pair<LocalDate, MoodState>> {
        var result: MutableList<Pair<LocalDate, MoodState>> = mutableListOf()

        val today = Utilities.getTodayDate()
        val lastDay = today.minusDays(7)

        var currentDate = today
        var count: Int = 0
        var averageMood: Int = 0

        for (entry in list) {
            if(entry.day == currentDate) {
                count++
                averageMood += getMoodInt(entry.moodState)
            }

            if (entry.day < currentDate) {
                val avrg = if (count == 0) 0 else averageMood / count
                result.add(Pair(currentDate, MoodState.values()[avrg]))
                count = 0
                averageMood = 0
                currentDate = entry.day

                if (currentDate <= lastDay) return result
            }
        }

        return result
    }

    private fun getMoodInt(moodState: MoodState): Int {
        return when (moodState){
            MoodState.V_GOOD -> 0
            MoodState.GOOD -> 1
            MoodState.OK -> 2
            MoodState.BAD -> 3
            MoodState.V_BAD -> 4
        }
    }

    private fun plotScatterGraph() {
        moodEntries = moodEntries!!.sortedWith(compareBy ({ it.day })).reversed()
        val lastWeekEntries = getLastWeekEntries(moodEntries!!)

        val dataSet = ArrayList<Entry>()

        var x: Float = 1.0f
        for (entry in lastWeekEntries) {
            dataSet.add(
                Entry(
                    x,
                    scatterGraph.getY(entry.second),
                    //ToDo: why is it so fucking big?!
                    resources.getDrawable(R.drawable.ic_face),
                    ""
                )
            )
            x += 5
        }

        val scatterDataSet = ScatterDataSet(dataSet, "")
        scatterDataSet.setDrawValues(false)

        scatterChart = binding.scatterChart
        scatterChart.data = ScatterData(scatterDataSet)
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
                markerView.moodEntryLabel.text = e.data.toString()
            }

            override fun onNothingSelected() {}
        })

    }
}