package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class Pinakbet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pinakbet)
        val pb= findViewById<TextView>(R.id.pinakbet_details)

        val pbHtml = getString(R.string.pinakbet_details)
        pb.text = HtmlCompat.fromHtml(pbHtml, HtmlCompat.FROM_HTML_MODE_COMPACT)

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, RecipePage::class.java)
            startActivity(intent)
        }
    }
}