package com.example.misa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var text_check_signup: TextView
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        text_check_signup = findViewById(R.id.newuser)

        // initialize firebase auth
        auth = FirebaseAuth.getInstance()


        // Login button click event
        btnLogin.setOnClickListener {
            val enteredUsername = edtEmail.text.toString()
            val enteredPassword = edtPassword.text.toString()

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(this, "Please fill All the details", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(enteredUsername, enteredPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Sign in successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, question::class.java))
                        } else {
                            Toast.makeText(
                                this,
                                "Sign up failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        text_check_signup.setOnClickListener() {
            startActivity(Intent(this, signup::class.java))
        }
    }
}
