package com.example.a3_quiz_app

data class Question(
    val id: Int, val question: String,
    val imgRes:Int,
    val optionOne: String, val optionTwo: String,
    val optionThree: String, val optionFour: String,
    val correctAnswer: Int
) {}
