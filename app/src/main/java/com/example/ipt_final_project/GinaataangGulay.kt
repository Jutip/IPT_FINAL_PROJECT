package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class GinaataangGulay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ginataanggulay)

        val gg= findViewById<TextView>(R.id.gg_details)

        val ggHtml = getString(R.string.ginataang_gulay_details)
        gg.text = HtmlCompat.fromHtml(ggHtml, HtmlCompat.FROM_HTML_MODE_COMPACT)

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, RecipePage::class.java)
            startActivity(intent)
        }
    }
}