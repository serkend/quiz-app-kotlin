package com.example.a3_quiz_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName:EditText = findViewById(R.id.etName)
        val startBtn: Button = findViewById(R.id.startBtn)

        startBtn.setOnClickListener {
            if(etName.text.isEmpty()) {
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USERNAME_KEY,etName.text.toString())
                startActivity(intent)
            }
        }
    }
}