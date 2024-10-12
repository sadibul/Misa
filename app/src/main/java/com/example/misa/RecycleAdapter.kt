package com.example.misa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter(
    private val question_list: ArrayList<question_data>, private val context: Context
) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question_A: TextView = itemView.findViewById(R.id.question_xml)
        val option1_A: TextView = itemView.findViewById(R.id.option1_xml)
        val option2_A: TextView = itemView.findViewById(R.id.option2_xml)
        val option3_A: TextView = itemView.findViewById(R.id.option3_xml)
        val option4_A: TextView = itemView.findViewById(R.id.option4_xml)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val question_Adapter = question_list[position]
        holder.apply {
            question_A.text = question_Adapter.question
            option1_A.text = question_Adapter.option1
            option2_A.text = question_Adapter.option2
            option3_A.text = question_Adapter.option3
            option4_A.text = question_Adapter.option4
        }

    }

    override fun getItemCount(): Int {
        return question_list.size
    }


}