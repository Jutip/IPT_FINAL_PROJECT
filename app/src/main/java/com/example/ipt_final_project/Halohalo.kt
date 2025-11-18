package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class Halohalo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halohalo)

        val haloHaloTitle = findViewById<TextView>(R.id.halohalo_title)
        val haloHaloImageView = findViewById<ImageView>(R.id.halohalo_pic)
        val haloHaloIngredients = findViewById<TextView>(R.id.halohalo_ingredients)

        haloHaloTitle.text = "Halo-Halo Recipe"
        haloHaloImageView.setImageResource(R.drawable.halohalo)

        val ingredientsHtml = getString(R.string.halo_halo_ingredients)
        haloHaloIngredients.text = HtmlCompat.fromHtml(ingredientsHtml, HtmlCompat.FROM_HTML_MODE_COMPACT)

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, RecipePage::class.java)
            startActivity(intent)
        }

    }
}
