package com.example.a3_quiz_app

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.a3_quiz_app.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mUsername: String = ""
    private var scores = 0
    private var correctAnswerCounter = 0

    private var options = ArrayList<TextView>()
    private var mSelectedOption: Int = 1
    private var correctAnswer: Int = 0
    private var mCurrentPosition: Int = 1
    private var clicked: Boolean = false

    private lateinit var binding: ActivityQuizQuestionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityQuizQuestionsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.optionOne.setOnClickListener(this)
        binding.optionTwo.setOnClickListener(this)
        binding.optionThree.setOnClickListener(this)
        binding.optionFour.setOnClickListener(this)
        binding.submitBtn.setOnClickListener(this)

        setQuestion()
        defaultOption()

        mUsername = intent.getStringExtra(Constants.USERNAME_KEY).toString()
    }

    private fun setQuestion() {
        defaultOption()
        val questions = Constants.getQuestions()
        var question = questions[mCurrentPosition - 1]
        correctAnswer = question.correctAnswer

        binding.questionTv.text = question.question
        binding.imageView.setImageResource(question.imgRes)
        binding.optionOne.text = question.optionOne
        binding.optionTwo.text = question.optionTwo
        binding.optionThree.text = question.optionThree
        binding.optionFour.text = question.optionFour
        binding.progreesBar.progress = mCurrentPosition
        binding.progressTV.text = "$mCurrentPosition/${binding.progreesBar.max}"
    }

    private fun defaultOption() {
        options.add(binding.optionOne)
        options.add(binding.optionTwo)
        options.add(binding.optionThree)
        options.add(binding.optionFour)

        for (o in options) {
            o.typeface = Typeface.DEFAULT
            o.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOption(textView: TextView, currentPosition: Int) {
        if (!clicked) {
            defaultOption()
            mSelectedOption = currentPosition

            textView.background =
                ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
            textView.setTypeface(textView.typeface, Typeface.BOLD)
        }
    }

    private fun checkCorrectAnswer() {
        var currOption = options[mSelectedOption - 1]
        if (!clicked) {
            if (mSelectedOption == 0) {
                Toast.makeText(this, "Choose answer!", Toast.LENGTH_SHORT).show()
            } else if (correctAnswer != mSelectedOption) {
                currOption.background =
                    ContextCompat.getDrawable(this, R.drawable.wrong_answer_clicked_bg)
            } else {
                correctAnswerCounter++
            }
            currOption = options[correctAnswer - 1]
            currOption.background =
                ContextCompat.getDrawable(this, R.drawable.right_answer_clicked_bg)

            when {
                mCurrentPosition != binding.progreesBar.max -> binding.submitBtn.text =
                    "Go to the next question"
                mCurrentPosition == binding.progreesBar.max -> binding.submitBtn.text =
                    "Finish"
            }
            clicked = true
        } else {
            mCurrentPosition++
            setQuestion()
            binding.submitBtn.text = "Submit"
            clicked = false
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.optionOne -> {
                selectedOption(findViewById<TextView>(R.id.optionOne), 1)
            }
            R.id.optionTwo -> {
                selectedOption(findViewById<TextView>(R.id.optionTwo), 2)
            }
            R.id.optionThree -> {
                selectedOption(findViewById<TextView>(R.id.optionThree), 3)
            }
            R.id.optionFour -> {
                selectedOption(findViewById<TextView>(R.id.optionFour), 4)
            }
            R.id.submitBtn -> {
                if (mCurrentPosition == binding.progreesBar.max && clicked) {
//                    Toast.makeText(this, "You have finished it", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(Constants.USERNAME_KEY, mUsername)
                    intent.putExtra(Constants.SCORE_KEY, correctAnswerCounter)
                    intent.putExtra(Constants.MAX_SCORE_KEY, binding.progreesBar.max)
                    startActivity(intent)
                } else checkCorrectAnswer()
            }
        }
    }
}