package com.example.hangman_piotr_krasnowski

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import java.util.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var language: String
    private lateinit var englishButton: RadioButton
    private lateinit var polishButton: RadioButton
    lateinit var locale : Locale
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

         englishButton = findViewById(R.id.englishButton)
         polishButton = findViewById(R.id.polishButton)

        englishButton.setOnClickListener(this)
        polishButton.setOnClickListener(this)

        val applyButton: Button = findViewById(R.id.buttonLanguage)
        applyButton.setOnClickListener{
            applyChanges()
            finish()
        }
    }

    private fun applyChanges() {


        var refresh = Intent(this, MainActivity::class.java)
        startActivity(refresh)
    }

    override fun onClick(p0: View?) {
        if(p0 ===  polishButton){

            setLocale("pl")
        }
        else if(p0 ===  englishButton){

            setLocale("en")
        }
    }

    private fun setLocale(s: String) {
        locale = Locale(s)
        var res = resources
        var dm = res.displayMetrics
        var conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf,dm)

    }
}