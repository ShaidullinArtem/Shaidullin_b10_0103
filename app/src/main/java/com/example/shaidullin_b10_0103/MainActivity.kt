package com.example.shaidullin_b10_0103

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.shaidullin_b10_0103.types.QuestionType
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var questionsArray = listOf<QuestionType>(
        QuestionType("Можно ли в c# наследоваться разу от 2 классов ?", false),
        QuestionType("Верно ли утверждение, что в c# 18 встроеннных типов данных ?", true),
        QuestionType("Верно ли утверждение, что в c# нет статических классов ?", false),
        QuestionType("В c# числа с плавующей точкой имеют тип Int ?", false),
        QuestionType("Метод .Split() разбивает строчку по переданому в него значению?", true),
        QuestionType("Метод .toLower() в c# приводит все символы строки к нижнему регистру", true),
        QuestionType("Метод .toUpper() в c# приводит все символы строки к нижнему регистру", false),
        QuestionType("Верно ли утверждение, что в c# нет абстрактных классов ?", false),
        QuestionType("Можно ли наследоваться от абстрактных классов в c# ?", true),
        QuestionType("Есть ли в c# интерфейсы", true),
    )

    private var questionIndex = Random.nextInt(questionsArray.size);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionText = findViewById<TextView>(R.id.mainQuestionTextView)

        questionText.setText(questionsArray[questionIndex].question)

        val buttonTrue = findViewById<Button>(R.id.trueBtn)
        val buttonFalse = findViewById<Button>(R.id.falseBtn)

        buttonTrue.setOnClickListener{
            onAnswer(true, questionsArray[questionIndex].answer)
        }

        buttonFalse.setOnClickListener{
            onAnswer(false, questionsArray[questionIndex].answer)
        }
    }

    private fun checkFinishState() {
        if (questionsArray.count() == 1) {
            val intent = Intent(this@MainActivity, FinishActivity::class.java)
            finish()
            return startActivity(intent)
        }
    }

    private fun onAnswer(answerType: Boolean, questionAnswer: Boolean) {

        var questionText = findViewById<TextView>(R.id.mainQuestionTextView);
        if (answerType == questionAnswer) {
            checkFinishState()
            questionsArray = questionsArray.filterIndexed { index, _ -> index != questionIndex }
            questionIndex = Random.nextInt(questionsArray.size)
            questionText.setText(questionsArray[questionIndex].question)
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show()
            questionIndex = Random.nextInt(questionsArray.size)
            questionText.setText(questionsArray[questionIndex].question)
        }
    }
}