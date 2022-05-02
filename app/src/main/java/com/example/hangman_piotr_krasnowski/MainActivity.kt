package com.example.hangman_piotr_krasnowski

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var format: String = "english"
    private var mysteryWord: String = " "
    private var letters: String = " "
    private var tries: Int = 0
    private var vector1 = Vector<String>()
    private var vector2 = Vector<String>()
    private var vector3 = Vector<String>()
    private var lettersInWord = Vector<String>()
    lateinit var stringArray: Array<String>
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var editLetter: EditText
    private lateinit var checkLetter: Button
    private lateinit var checkWord: Button
    private lateinit var newWord: FloatingActionButton
    private lateinit var spinner: Spinner
    private var help: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stringArray = resources.getStringArray(R.array.words)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)
        editLetter = findViewById(R.id.editLetter)
        checkLetter = findViewById(R.id.checkLetter)
        checkWord = findViewById(R.id.checkWord)
        spinner = findViewById(R.id.spinner)
        newWord = findViewById(R.id.newWord)
        spinnerFunction()
        newWord.setOnClickListener {

            when(help){
                0 -> getRandomWord1()
                1 -> getRandomWord2()
                2 -> getRandomWord3()
            }
            startGame()
        }
        checkWord.setOnClickListener(this)
        checkLetter.setOnClickListener(this)

    }

    private fun spinnerFunction() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (position) {
                    0 -> {
                        getRandomWord1()
                        help = 0
                    }
                    1 -> {
                        getRandomWord2()
                        help = 1
                    }
                    2 -> {
                        getRandomWord3()
                        help = 2
                    }
                    else ->{
                        getRandomWord1()
                        help = 0
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun startGame() {


        imageView.setImageResource(R.drawable.hangman0)
        letters = " "
        lettersInWord.clear()
        tries = 0
        repeat(mysteryWord.length){
            letters += "*"
        }
        textView.text = letters
    }

    private fun play() {
        val guess = editLetter.text.toString()
        editLetter.text = null

        if(guess.length > 1){
            if(guess == mysteryWord){
                Snackbar.make(
                    findViewById(R.id.mainContainer),
                    getString(R.string.won),
                    Snackbar.LENGTH_LONG
                ).show()
                return
             }
        }
        if(guess.length == 1){
            var counter = 0
            for(item in mysteryWord.indices) {
                if (mysteryWord[item] == guess[0]) {
                    lettersInWord.addElement(guess)
                    counter++
                }
            }
            if(lettersInWord.size == mysteryWord.length){
                Snackbar.make(
                    findViewById(R.id.mainContainer),
                    getString(R.string.won),
                    Snackbar.LENGTH_LONG
                ).show()
                refreshWord()
                return
            }
            if(counter == 0){
                tries++;
                refreshDraw(tries)
            }
            refreshWord()
            return
        }
    }

    private fun refreshDraw(tries: Int) {
        when (tries) {
            1 -> imageView.setImageResource(R.drawable.hangman1)
            2 -> imageView.setImageResource(R.drawable.hangman2)
            3 -> imageView.setImageResource(R.drawable.hangman3)
            4 -> imageView.setImageResource(R.drawable.hangman4)
            5 -> imageView.setImageResource(R.drawable.hangman5)
            6 -> imageView.setImageResource(R.drawable.hangman6)
            7 -> imageView.setImageResource(R.drawable.hangman7)
            8 -> imageView.setImageResource(R.drawable.hangman8)
            9 -> imageView.setImageResource(R.drawable.hangman9)
            10 -> {
                imageView.setImageResource(R.drawable.hangman10)
                Snackbar.make(
                    findViewById(R.id.mainContainer),
                    getString(R.string.lost),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun refreshWord() {
        letters = ""
        for(item in mysteryWord.indices){
            if(lettersInWord.contains(mysteryWord[item].toString())){
                letters += mysteryWord[item]
            }
            else{
                letters += "*"
            }
        }
        textView.text = letters
    }

    private fun getRandomWord3() {
        for (i in stringArray.indices) {
            if (stringArray[i].length > 6) {
                vector3.addElement(stringArray[i])
            }
        }
        val number = (0..vector3.size).random()
         mysteryWord = vector3[number]
    }

    private fun getRandomWord2(){
        for (i in stringArray.indices) {
            if (stringArray[i].length > 3 && stringArray[i].length <= 6) {
                vector2.addElement(stringArray[i])
            }
        }
        val number = (0..vector2.size).random()
        mysteryWord = vector2[number]
    }

    private fun getRandomWord1() {
        for (i in stringArray.indices) {
            if (stringArray[i].length <= 3) {
                vector1.addElement(stringArray[i])
            }
        }
        val number = (0..vector1.size).random()
        mysteryWord = vector1[number]
    }

    override fun onClick(p0: View?) {
        if(p0 === checkWord) {
            textView.text = mysteryWord
            Snackbar.make(
                findViewById(R.id.mainContainer),
                getString(R.string.lost),
                Snackbar.LENGTH_LONG
            ).show()
        }
        if(p0 === checkLetter){
            play()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.translateItem -> startSettingsActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSettingsActivity() {
        val intent: Intent = Intent(this, SettingsActivity::class.java)

        startActivity(intent)
    }

}
