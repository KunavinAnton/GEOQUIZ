package com.bignerdranch.android.geomain

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val KEY_INDEX = "index"

    private lateinit var trueButton:Button
    private lateinit var  falseButton:Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView


    private fun showResult(countCorrect:Int, countAnswers:Int){

        Thread.sleep(3000)
        val result = "Your score: ${(countCorrect * 100) / countAnswers}%"
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        Log.d(TAG, "onCreate(Bundle?) called 111111111111111111")

        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }
        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        updateQuestion()
    }
    private fun updateQuestion(){
        trueButton.isEnabled = true
        falseButton.isEnabled = true
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer:Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        trueButton.isEnabled = false
        falseButton.isEnabled = false
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        quizViewModel.incAnswers()
        if (messageResId == R.string.correct_toast) quizViewModel.incCorrectAnswers()
        if (quizViewModel.countAnswers == quizViewModel.currentQuestionBankSize) {
            showResult(quizViewModel.countCorrect, quizViewModel.countAnswers)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart called 111111111111111111")
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume called 111111111111111111")
    }
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause called 111111111111111111")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop called 111111111111111111")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy called 111111111111111111")
    }



}