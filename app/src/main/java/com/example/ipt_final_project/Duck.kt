package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Duck : AppCompatActivity() {

    lateinit var backdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_duck)

        backdd = findViewById(R.id.backdd)
        backdd.setOnClickListener {
            val intent = Intent(this, RecipePage::class.java)
            startActivity(intent)
        }
    }
}