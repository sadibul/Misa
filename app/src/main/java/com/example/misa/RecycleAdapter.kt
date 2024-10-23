package com.example.misa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecycleAdapter(
    private val question_list: ArrayList<question_data>, private val context: Context
) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question_A: TextView = itemView.findViewById(R.id.question_xml)
        val option1_A: RadioButton = itemView.findViewById(R.id.option1_xml)
        val option2_A: RadioButton = itemView.findViewById(R.id.option2_xml)
        val option3_A: RadioButton = itemView.findViewById(R.id.option3_xml)
        val option4_A: RadioButton = itemView.findViewById(R.id.option4_xml)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_question, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return question_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question_Adapter = question_list[position]

        // Set question and options text
        holder.apply {
            question_A.text = question_Adapter.question
            option1_A.text = question_Adapter.option1
            option2_A.text = question_Adapter.option2
            option3_A.text = question_Adapter.option3
            option4_A.text = question_Adapter.option4
        }

        // RadioButton Click Listeners
        holder.option1_A.setOnClickListener {
            question_Adapter.selectedOption = 1
            notifyItemChanged(position) // Update the state of the item
        }

        holder.option2_A.setOnClickListener {
            question_Adapter.selectedOption = 2
            notifyItemChanged(position) // Update the state of the item
        }

        holder.option3_A.setOnClickListener {
            question_Adapter.selectedOption = 3
            notifyItemChanged(position) // Update the state of the item
        }

        holder.option4_A.setOnClickListener {
            question_Adapter.selectedOption = 4
            notifyItemChanged(position) // Update the state of the item
        }

        // Set checked state based on selectedOption
        holder.option1_A.isChecked = question_Adapter.selectedOption == 1
        holder.option2_A.isChecked = question_Adapter.selectedOption == 2
        holder.option3_A.isChecked = question_Adapter.selectedOption == 3
        holder.option4_A.isChecked = question_Adapter.selectedOption == 4
    }
}