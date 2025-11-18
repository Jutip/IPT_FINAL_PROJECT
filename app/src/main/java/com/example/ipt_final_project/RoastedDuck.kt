package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class RoastedDuck : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_roasted_duck)
        val rd= findViewById<TextView>(R.id.roastedduck_details)

        val rdHtml = getString(R.string.roasted_duck_details)
        rd.text = HtmlCompat.fromHtml(rdHtml, HtmlCompat.FROM_HTML_MODE_COMPACT)

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, RecipePage::class.java)
            startActivity(intent)
        }
    }
}