package com.bignerdranch.android.geomain

import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val KEY_INDEX = "index"
    private val VALUE_BUTTON_TRUE = "TrueButton"
    private val VALUE_BUTTON_FALSE = "FalseButton"
    private val VALUE_BUTTON_NEXT = "NextButton"
    private val VALUE_BUTTON_PREV = "PrevButton"
    private val VALUE_TEXT_VIEW = "TextViewButton"

    private fun showResult(countCorrect:Int, countAnswers:Int){
        quizViewModel.lockAllButtons()

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

        quizViewModel.trueButton = findViewById(R.id.true_button)
        quizViewModel.falseButton = findViewById(R.id.false_button)
        quizViewModel.nextButton = findViewById(R.id.next_button)
        quizViewModel.prevButton = findViewById(R.id.prev_button)
        quizViewModel.questionTextView = findViewById(R.id.question_text_view)

        quizViewModel.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        quizViewModel.falseButton .setOnClickListener {
            checkAnswer(false)
        }
        quizViewModel.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        quizViewModel.nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        quizViewModel.prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        updateQuestion()
    }
    private fun updateQuestion(){
        quizViewModel.lockButtons(true)

        val questionTextResId = quizViewModel.currentQuestionText
        quizViewModel.questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer:Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        quizViewModel.lockButtons(false)
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
        savedInstanceState.putBoolean(VALUE_BUTTON_TRUE, quizViewModel.trueButton.isEnabled)
        savedInstanceState.putBoolean(VALUE_BUTTON_FALSE, quizViewModel.falseButton.isEnabled)
        savedInstanceState.putBoolean(VALUE_BUTTON_NEXT, quizViewModel.nextButton.isEnabled)
        savedInstanceState.putBoolean(VALUE_BUTTON_PREV, quizViewModel.prevButton.isEnabled)
        savedInstanceState.putBoolean(VALUE_TEXT_VIEW, quizViewModel.questionTextView.isEnabled)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        quizViewModel.trueButton.isEnabled = savedInstanceState.getBoolean(VALUE_BUTTON_TRUE, true)
        quizViewModel.falseButton.isEnabled = savedInstanceState.getBoolean(VALUE_BUTTON_FALSE, true)
        quizViewModel.nextButton.isEnabled = savedInstanceState.getBoolean(VALUE_BUTTON_NEXT, true)
        quizViewModel.prevButton.isEnabled = savedInstanceState.getBoolean(VALUE_BUTTON_PREV, true)
        quizViewModel.questionTextView.isEnabled = savedInstanceState.getBoolean(VALUE_TEXT_VIEW, true)
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