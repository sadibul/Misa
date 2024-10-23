package com.example.misa

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.pow
import kotlin.math.sqrt

class GaugeActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gauge)

        pieChart = findViewById(R.id.pieChart_xml)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("Result1").document("User_score").get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val userScores = document.data?.get(uid) as? ArrayList<Map<String, Any>> ?: arrayListOf()
                    if (userScores.isNotEmpty()) {
                        val latestScore = userScores.last()["score"] as? Long ?: 0
                        displayScoreInPieChart(latestScore.toInt())
                    }
                }
            }
        }

        // Add touch listener to the PieChart to detect clicks in the center area
        pieChart.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (isPointInCenter(event.x, event.y)) {
                    // Navigate to BarChartActivity if the center text is clicked
                    startActivity(Intent(this, BarChartActivity::class.java))
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    // Method to display score in PieChart
    private fun displayScoreInPieChart(userScore: Int) {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f

        pieChart.setDrawCenterText(true)
        pieChart.setRotationAngle(0f)
        pieChart.isRotationEnabled = false
        pieChart.isHighlightPerTapEnabled = true

        pieChart.animateY(1400, Easing.EaseOutSine)
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        val entries = arrayListOf<PieEntry>(
            PieEntry(25f, "Happy"),
            PieEntry(25f, "Sad"),
            PieEntry(25f, "Anxious"),
            PieEntry(25f, "Relaxed")
        )

        val dataSet = PieDataSet(entries, "Moods")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.setDrawValues(false)

        val colors = arrayListOf<Int>()
        colors.add(resources.getColor(R.color.purple_200))
        colors.add(resources.getColor(R.color.red))
        colors.add(resources.getColor(R.color.yellow))
        colors.add(resources.getColor(R.color.blue))
        dataSet.colors = colors

        val data = PieData(dataSet)
        pieChart.data = data

        val moodIndex = when (userScore) {
            in 0..24 -> 1  // Sad
            in 25..50 -> 2  // Anxious
            in 51..75 -> 3  // Relaxed
            in 76..100 -> 0  // Happy
            else -> -1
        }

        if (moodIndex != -1) {
            pieChart.highlightValues(arrayOf(com.github.mikephil.charting.highlight.Highlight(moodIndex.toFloat(), 0, 0)))
        }

        pieChart.centerText = "Score: $userScore"
        pieChart.setCenterTextSize(20f)
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)

        pieChart.invalidate()
    }

    // Helper function to check if touch point is in the center
    private fun isPointInCenter(x: Float, y: Float): Boolean {
        val centerX = pieChart.width / 2f
        val centerY = pieChart.height / 2f
        val radius = pieChart.radius * 0.6f  // Adjust as needed

        val distance = sqrt(
            (x - centerX).toDouble().pow(2.0) + (y - centerY).toDouble()
            .pow(2.0)
        )
        return distance <= radius
    }
}
