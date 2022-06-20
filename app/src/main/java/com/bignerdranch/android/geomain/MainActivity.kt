package com.bignerdranch.android.geomain

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var trueButton:Button
    private lateinit var  falseButton:Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

    private  var countCorrect:Int = 0
    private var countAnswers: Int = 0
    private var currentIndex = 0

    private  val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )


    private fun showResult(countCorrect:Int, countAnswers:Int){
        trueButton.isEnabled = false
        falseButton.isEnabled = false
        nextButton.isEnabled = false
        prevButton.isEnabled = false
        questionTextView.isEnabled = false

        Thread.sleep(3000)
        val result = "Your score: ${(countCorrect * 100) / countAnswers}%"
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        prevButton.setOnClickListener {
            if(currentIndex == 0) currentIndex =+ questionBank.size
            currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }
    private fun updateQuestion(){
        trueButton.isEnabled = true
        falseButton.isEnabled = true
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer:Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        trueButton.isEnabled = false
        falseButton.isEnabled = false
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        countAnswers += 1
        if (messageResId == R.string.correct_toast) countCorrect += 1
        if (countAnswers == questionBank.size) {
            showResult(countCorrect, countAnswers)
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
    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop called 111111111111111111")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy called 111111111111111111")
    }



}