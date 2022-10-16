package com.example.a3_quiz_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a3_quiz_app.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultBinding
    private var mUsername = ""
    private var scores = 0
    private var maxScores = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scores = intent.getIntExtra(Constants.SCORE_KEY, 0)
        maxScores = intent.getIntExtra(Constants.MAX_SCORE_KEY, 0)
        mUsername = intent.getStringExtra(Constants.USERNAME_KEY).toString()

        binding.usernameTV.text = mUsername
        binding.scoreTV.text = "Your Score is $scores out of $maxScores."

        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}