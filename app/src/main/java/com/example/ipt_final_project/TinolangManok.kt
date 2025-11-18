package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class TinolangManok : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tinolang_manok)
        val tm= findViewById<TextView>(R.id.tinola_details)

        val tmHtml = getString(R.string.tinolang_manok_details)
        tm.text = HtmlCompat.fromHtml(tmHtml, HtmlCompat.FROM_HTML_MODE_COMPACT)

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, RecipePage::class.java)
            startActivity(intent)
        }
    }
}