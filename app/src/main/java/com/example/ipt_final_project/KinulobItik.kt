package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class KinulobItik : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kinulob_itik)
        val ki= findViewById<TextView>(R.id.ki_details)

        val kiHtml = getString(R.string.kinulob_na_itik_details)
        ki.text = HtmlCompat.fromHtml(kiHtml, HtmlCompat.FROM_HTML_MODE_COMPACT)

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, RecipePage::class.java)
            startActivity(intent)
        }
    }
}