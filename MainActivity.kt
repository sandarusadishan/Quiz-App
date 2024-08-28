package com.example.quiseapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var questionTextView: TextView
    private lateinit var totalQuestionTextView: TextView
    private lateinit var ansA: Button
    private lateinit var ansB: Button
    private lateinit var ansC: Button
    private lateinit var ansD: Button
    private lateinit var ansE: Button
    private lateinit var btn_submit: Button

    private var score = 0
    private val totalQuestion = QuestionAnswers.question.size
    private var currentQuestionIndex = 0
    private var selectedAnswer = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        totalQuestionTextView = findViewById(R.id.total_questions)
        questionTextView = findViewById(R.id.total_questions)
        ansA = findViewById(R.id.ans_a)
        ansB = findViewById(R.id.ans_b)
        ansC = findViewById(R.id.ans_c)
        ansD = findViewById(R.id.ans_d)
        ansE = findViewById(R.id.ans_e)
        btn_submit = findViewById(R.id.btn_submit)

        totalQuestionTextView.text = "Total question:" + totalQuestion

        loadNewQuestion()

        // Set OnClickListener for buttons
        ansA.setOnClickListener(this)
        ansB.setOnClickListener(this)
        ansC.setOnClickListener(this)
        ansD.setOnClickListener(this)
        ansE.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    private fun loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz()
            return
        }

        questionTextView.text = QuestionAnswers.question[currentQuestionIndex]
        ansA.text = QuestionAnswers.choices[currentQuestionIndex][0]
        ansB.text = QuestionAnswers.choices[currentQuestionIndex][1]
        ansC.text = QuestionAnswers.choices[currentQuestionIndex][2]
        ansD.text = QuestionAnswers.choices[currentQuestionIndex][3]
        ansE.text = QuestionAnswers.choices[currentQuestionIndex][4]

        selectedAnswer = ""
    }

    private fun finishQuiz() {
        val passStatus = if (score >= totalQuestion * 0.6) "Passed" else "Fail"

        AlertDialog.Builder(this)
            .setTitle(passStatus)
            .setMessage("Your Score is $score out of $totalQuestion")
            .setPositiveButton("Restart") { dialog, _ ->
                restartQuiz()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun restartQuiz() {
        score = 0
        currentQuestionIndex = 0
        loadNewQuestion()
    }

    override fun onClick(view: View) {
        ansA.setBackgroundColor(Color.WHITE)
        ansB.setBackgroundColor(Color.WHITE)
        ansC.setBackgroundColor(Color.WHITE)
        ansD.setBackgroundColor(Color.WHITE)
        ansE.setBackgroundColor(Color.WHITE)

        val clickedButton = view as Button

        if (clickedButton.id == R.id.btn_submit) {
            if (selectedAnswer.isNotEmpty()) {
                if (selectedAnswer == QuestionAnswers.correctAnswers[currentQuestionIndex]) {
                    score++
                } else {
                    clickedButton.setBackgroundColor(Color.MAGENTA)
                }
                currentQuestionIndex++
                loadNewQuestion()
            }
        } else {
            selectedAnswer = clickedButton.text.toString()
            clickedButton.setBackgroundColor(Color.YELLOW)
        }
    }
}
