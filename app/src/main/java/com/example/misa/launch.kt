package com.example.misa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button

class launch : AppCompatActivity() {

    private lateinit var btnSignUp: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch) // Ensure your XML layout is named activity_launch.xml

        val sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
        val isSignedUp = sharedPreferences.getBoolean("isSignedUp", false)

        if (isSignedUp) {
            // Redirect to LoginActivity if already signed up
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        } else {
            // Otherwise, continue to the Launch page layout
            setContentView(R.layout.activity_launch)
        }

        // Initialize buttons
        btnSignUp = findViewById(R.id.btnSignUp)
        btnLogin = findViewById(R.id.btnLogin)

        // Set up onClick listeners for buttons
        btnSignUp.setOnClickListener {
            // Navigate to the SignUp Activity
            val signUpIntent = Intent(this, signup::class.java)
            startActivity(signUpIntent)
        }



        btnLogin.setOnClickListener {
            // Navigate to the Login Activity
            val loginIntent = Intent(this, login::class.java)
            startActivity(loginIntent)
        }
    }
}