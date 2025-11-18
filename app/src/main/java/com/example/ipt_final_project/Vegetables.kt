package com.example.ipt_final_project


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Vegetables : AppCompatActivity() {

    lateinit var backv: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vegetables)

        backv = findViewById(R.id.backv)
        backv.setOnClickListener {
            val intent = Intent(this, RecipePage::class.java)
            startActivity(intent)
        }
    }
}