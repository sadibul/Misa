package com.example.misa


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class question : AppCompatActivity(), View.OnClickListener {

    private val radio_buttons = arrayOfNulls<RadioButton>(4)


    private lateinit var next_button: Button
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Mood)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // initialization
        next_button = findViewById(R.id.next_xml)
        option1 = findViewById(R.id.happy_xml)
        option2 = findViewById(R.id.sick_xml)
        option3 = findViewById(R.id.sad_xml)
        option4 = findViewById(R.id.angry_xml)

        next_button.setOnClickListener {
            startActivity(Intent(this, GaugeActivity::class.java))
        }
        implementation()
    }

    private fun implementation() {

        radio_buttons[0] = option1
        radio_buttons[1] = option2
        radio_buttons[2] = option3
        radio_buttons[3] = option4

        radio_buttons.forEach { it?.setOnClickListener(this) } // Setting the click listener on each RadioButton

    }

    override fun onClick(view: View?) {
        val button_clicked = view as RadioButton
        for (button in radio_buttons) { // Ensuring only one RadioButton is selected at a time
            if (button != null) {
                if (button.id == button_clicked.id) {
                    // Check the clicked button
                    button.isChecked = true
                } else {
                    // Uncheck other buttons
                    button.isChecked = false
                }
            }
        }
    }
}