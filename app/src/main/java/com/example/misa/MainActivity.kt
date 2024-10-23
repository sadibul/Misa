package com.example.misa

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val questionList = arrayListOf<question_data>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleAdapter: RecycleAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var currentFolder = "Folder1"  // Default to Folder1; can be updated dynamically

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        recycleAdapter = RecycleAdapter(questionList, this)
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        findViewById<Button>(R.id.next_xml_recycle).setOnClickListener {
            saveUserScoreToFirestore()
        }
        getQuestionsFromFirestore()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getQuestionsFromFirestore() {
        db.collection(currentFolder).get().addOnSuccessListener { result ->
            questionList.clear()
            for (document in result) {
                val question = document.toObject(question_data::class.java)
                questionList.add(question)
            }
            recycleAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun saveUserScoreToFirestore() {
        val uid = auth.currentUser?.uid ?: return
        val userScoreRef = db.collection("Result1").document("User_score")

        var totalPoints = 0
        for (question in questionList) {
            totalPoints += when (question.selectedOption) {
                1 -> 13
                2 -> 4
                3 -> 18
                4 -> 10
                else -> 0
            }
        }

        // Get today's date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val today = dateFormat.format(Date())

        // Now store the score with the date
        userScoreRef.get().addOnSuccessListener { doc ->
            val userScores = if (doc.exists()) {
                doc.data?.get(uid) as? ArrayList<Map<String, Any>> ?: arrayListOf()
            } else {
                arrayListOf()
            }

            userScores.add(mapOf("date" to today, "score" to totalPoints))
            userScoreRef.set(mapOf(uid to userScores)).addOnSuccessListener {
                // After saving, navigate to Pie chart activity
                val intent = Intent(this, GaugeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
