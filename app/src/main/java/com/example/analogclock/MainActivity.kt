package com.example.analogclock

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        val layout = findViewById<LinearLayout>(R.id.main_layout)

        val clocks = AnalogClockView(applicationContext)

        layout.addView(clocks)
    }



}