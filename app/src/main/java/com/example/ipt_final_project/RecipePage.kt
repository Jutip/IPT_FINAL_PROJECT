package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat



class RecipePage : AppCompatActivity() {
    lateinit var chickenAdobo: ImageView
    lateinit var sisig: ImageView
    lateinit var ginaataangGulay: ImageView
    lateinit var humba: ImageView
    lateinit var sinigangBangus: ImageView
    lateinit var pataPorkBeans: ImageView
    lateinit var bicolExpress: ImageView
    lateinit var patotin: ImageView
    lateinit var chicken: Button
    private lateinit var adapter: SearchItem
    private val data = listOf("Chicken", "Pork", "Beef", "Fish", "Duck", "Vegetables", "Desserts")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_page)
        val searchItems = listOf("Chicken", "Pork", "Beef", "Fish", "Duck", "Vegetables", "Desserts")
        val searchAutoComplete = findViewById<AutoCompleteTextView>(R.id.searchAutoComplete)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, searchItems)
        searchAutoComplete.setAdapter(adapter)

        searchAutoComplete.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val intent = Intent(this, Chicken::class.java)
            intent.putExtra("selectedItem", selectedItem)
            startActivity(intent)
        }

        chicken = findViewById(R.id.chicken)
        chicken.setOnClickListener {
            val intent = Intent(this, Chicken::class.java)
            startActivity(intent)
        }

        chickenAdobo = findViewById(R.id.chickenAdobo)
        chickenAdobo.setOnClickListener {
            val intent = Intent(this, ChickenAdobo::class.java)
            startActivity(intent)
        }

        sisig = findViewById(R.id.sisig)
        sisig.setOnClickListener {
            val intent = Intent(this, Sisig::class.java)
            startActivity(intent)
        }

        ginaataangGulay= findViewById(R.id.ginataangGulay)
        ginaataangGulay.setOnClickListener {
            val intent = Intent(this, GinaataangGulay::class.java)
            startActivity(intent)
        }

        humba = findViewById(R.id.humba)
        humba.setOnClickListener {
            val intent = Intent(this, Humba::class.java)
            startActivity(intent)
        }

        sinigangBangus = findViewById(R.id.sinigangBangus)
        sinigangBangus.setOnClickListener {
            val intent = Intent(this, SinigangBangus::class.java)
            startActivity(intent)
        }

        pataPorkBeans = findViewById(R.id.pata)
        pataPorkBeans.setOnClickListener {
            val intent = Intent(this, PataPorkBeans::class.java)
            startActivity(intent)
        }

        bicolExpress = findViewById(R.id.bicol)
        bicolExpress.setOnClickListener {
            val intent = Intent(this, BicolExpress::class.java)
            startActivity(intent)
        }

        patotin = findViewById(R.id.patotin)
        patotin.setOnClickListener {
            val intent = Intent(this, Patotin::class.java)
            startActivity(intent)
        }

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

    }
}