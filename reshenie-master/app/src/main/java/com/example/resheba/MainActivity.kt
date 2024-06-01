package com.example.resheba

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var problemTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var startButton: Button
    private lateinit var checkButton: Button
    private lateinit var correctAnswerTextView: TextView
    private lateinit var statisticsTextView: TextView

    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private var currentAnswer = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        problemTextView = findViewById(R.id.problemTextView)
        answerEditText = findViewById(R.id.answerEditText)
        startButton = findViewById(R.id.startButton)
        checkButton = findViewById(R.id.checkButton)
        correctAnswerTextView = findViewById(R.id.correctAnswerTextView)
        statisticsTextView = findViewById(R.id.statisticsTextView)

        startButton.setOnClickListener {
            generateProblem()
            startButton.isEnabled = false
            checkButton.isEnabled = true
            answerEditText.isEnabled = true
            problemTextView.setBackgroundColor(Color.WHITE)
            correctAnswerTextView.text = "Правильный ответ:"
        }

        checkButton.setOnClickListener {
            checkAnswer()
            startButton.isEnabled = true
            checkButton.isEnabled = false
            answerEditText.isEnabled = false
        }
    }

    private fun generateProblem() {
        val num1 = Random.nextInt(10, 100)
        val num2 = Random.nextInt(10, 100)
        val operatorIndex = Random.nextInt(4)
        val operator = when (operatorIndex) {
            0 -> "+"
            1 -> "-"
            2 -> "*"
            3 -> "/"
            else -> "+"
        }

        currentAnswer = when (operator) {
            "+" -> num1 + num2.toDouble()
            "-" -> num1 - num2.toDouble()
            "*" -> num1 * num2.toDouble()
            "/" -> {
                var n1 = num1
                var n2 = num2
                while (n1 % n2 != 0) {
                    n1 = Random.nextInt(10, 100)
                    n2 = Random.nextInt(10, 100)
                }
                n1 / n2.toDouble()
            }
            else -> 0.0
        }

        val problemText = "$num1 $operator $num2"
        problemTextView.text = problemText
        answerEditText.text.clear()
    }

    private fun checkAnswer() {
        val userAnswer = answerEditText.text.toString().toDoubleOrNull()
        if (userAnswer != null && userAnswer == currentAnswer) {
            problemTextView.setBackgroundColor(Color.GREEN)
            correctAnswers++
        } else {
            problemTextView.setBackgroundColor(Color.RED)
            incorrectAnswers++
        }
        correctAnswerTextView.text = "Правильный ответ: $currentAnswer"
        updateStatistics()
    }
//13231
    private fun updateStatistics() {
        val totalAnswers = correctAnswers + incorrectAnswers
        val correctPercentage = if (totalAnswers > 0) {
            String.format("%.2f", (correctAnswers.toDouble() / totalAnswers * 100))
        } else {
            "0.00"
        }
        statisticsTextView.text = "Правильных ответов: $correctAnswers\nНеправильных ответов: $incorrectAnswers\nПроцент правильных: $correctPercentage%"
    }
}
