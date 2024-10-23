package com.example.misa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Color

class BarChartActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        barChart = findViewById(R.id.barChart)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        loadDataAndDisplayChart()
    }

    private fun loadDataAndDisplayChart() {
        val uid = auth.currentUser?.uid ?: return
        val userScoreRef = db.collection("Result1").document("User_score")

        userScoreRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // Retrieve the array list of score data from Firestore
                val userScores =
                    document.data?.get(uid) as? ArrayList<Map<String, Any>> ?: arrayListOf()

                val entries = ArrayList<BarEntry>()
                val labels = ArrayList<String>()

                // Loop through the score data and create entries for the bar chart
                for ((index, scoreData) in userScores.withIndex()) {
                    val date = scoreData["date"] as? String ?: "Unknown Date"
                    val score = (scoreData["score"] as? Long)?.toFloat() ?: 0f

                    // Add the score and date to the chart data
                    entries.add(BarEntry(index.toFloat(), score))
                    labels.add(date) // Store the date for X-axis labels
                }

                // Prepare the bar chart dataset
                val dataSet = BarDataSet(entries, "Mood Scores")
                dataSet.color =
                    resources.getColor(R.color.colorPrimary) // Optional: Customize the bar color

                // Set value formatter to display integer values above the bars
                dataSet.valueFormatter =
                    object : com.github.mikephil.charting.formatter.ValueFormatter() {
                        override fun getBarLabel(barEntry: BarEntry?): String {
                            return barEntry?.y?.toInt()
                                .toString() // Convert float to int for the label
                        }
                    }

                val barData = BarData(dataSet)

                // Set the data and refresh the chart
                barChart.data = barData
                barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                barChart.xAxis.granularity = 1f
                barChart.xAxis.setDrawGridLines(false)
                barChart.axisRight.isEnabled = false
                barChart.axisLeft.axisMinimum = 0f
                barChart.axisLeft.axisMaximum = 100f
                barChart.axisLeft.granularity = 10f
                barChart.description.isEnabled = false // Remove the description label
                barChart.axisLeft.setDrawGridLines(true)
                barChart.axisLeft.gridColor = Color.ALPHA_FIELD_NUMBER
                barChart.animateY(1500, Easing.EaseInOutQuad)
                barChart.setExtraOffsets(10f, 10f, 10f, 10f)
                barChart.legend.textColor = Color.BLUE_FIELD_NUMBER
                barChart.setDrawBorders(true)
                barChart.setBorderColor(Color.GREEN_FIELD_NUMBER)
                barChart.setBorderWidth(2f)
                barChart.invalidate()
            }
        }
    }


}
