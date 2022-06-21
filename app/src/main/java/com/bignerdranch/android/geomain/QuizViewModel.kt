package com.bignerdranch.android.geomain

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel


class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var countCorrect:Int = 0
    var countAnswers: Int = 0

    @SuppressLint("StaticFieldLeak")
    lateinit var trueButton: Button
    @SuppressLint("StaticFieldLeak")
    lateinit var  falseButton: Button
    @SuppressLint("StaticFieldLeak")
    lateinit var nextButton: Button
    @SuppressLint("StaticFieldLeak")
    lateinit var prevButton: Button
    @SuppressLint("StaticFieldLeak")
    lateinit var questionTextView: TextView

    private  val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionBankSize: Int get()= questionBank.size

    val currentQuestionAnswer: Boolean get() =
        questionBank[currentIndex].answer
    val currentQuestionText: Int get() =
        questionBank[currentIndex].textResId
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev(){
        if(currentIndex == 0) currentIndex =+ questionBank.size
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun incAnswers() {countAnswers += 1}
    fun incCorrectAnswers() {countCorrect += 1}

    fun lockButtons(lock:Boolean){
        trueButton.isEnabled = lock
        falseButton.isEnabled = lock
    }
    fun lockAllButtons(){
        trueButton.isEnabled = false
        falseButton.isEnabled = false
        nextButton.isEnabled = false
        prevButton.isEnabled = false
        questionTextView.isEnabled = false
    }
}