package com.example.misa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val Question_List = arrayListOf<question_data>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleAdapter: RecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.question)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recycler_view) // from the main list of xml Recyclerview

        Question_List.apply {
            add(
                question_data(
                    "Which of the following aspects of our product/service did you find most impressive?",
                    "Speed and Efficiency",
                    "User-Friendly Interface",
                    "Quality and Reliability",
                    "Pricing and Value for Money User-Friendly Interface User-Friendly Interface"
                )
            )
            add(
                question_data(
                    "Which of the following aspects of our product/service did you find most impressive?",
                    "Speed and Efficiency",
                    "User-Friendly Interface",
                    "Quality and Reliability",
                    "Pricing and Value for Money"
                )
            )
            add(
                question_data(
                    "Which of the following aspects of our product/service did you find most impressive?",
                    "Speed and Efficiency",
                    "User-Friendly Interface",
                    "Quality and Reliability",
                    "Pricing and Value for Money"
                )
            )
            add(
                question_data(
                    "Which of the following aspects of our product/service did you find most impressive?",
                    "Speed and Efficiency",
                    "User-Friendly Interface",
                    "Quality and Reliability",
                    "Pricing and Value for Money"
                )
            )
            add(
                question_data(
                    "Which of the following aspects of our product/service did you find most impressive?",
                    "Speed and Efficiency",
                    "User-Friendly Interface",
                    "Quality and Reliability",
                    "Pricing and Value for Money"
                )
            )


        }

        recycleAdapter = RecycleAdapter(Question_List, this)
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}