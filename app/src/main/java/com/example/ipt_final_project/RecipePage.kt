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
    lateinit var Sisig: ImageView
    lateinit var GinaataangGulay: ImageView
    lateinit var Humba: ImageView
    lateinit var SinigangBangus: ImageView
    lateinit var PataPorkBeans: ImageView
    lateinit var BicolExpress: ImageView
    lateinit var Patotin: ImageView
    lateinit var Chicken: Button
    private lateinit var adapter: SearchItem
    private val data = listOf("Chicken", "Pork", "Beef", "Fish", "Duck", "Vegestables", "Desserts")



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

        Chicken = findViewById(R.id.chicken)
        Chicken.setOnClickListener {
            val intent = Intent(this, Chicken::class.java)
            startActivity(intent)
        }

        chickenAdobo = findViewById(R.id.chickenAdobo)
        chickenAdobo.setOnClickListener {
            val intent = Intent(this, ChickenAdobo::class.java)
            startActivity(intent)
        }

        Sisig = findViewById(R.id.sisig)
        Sisig.setOnClickListener {
            val intent = Intent(this, Sisig::class.java)
            startActivity(intent)
        }

        GinaataangGulay= findViewById(R.id.ginataangGulay)
        GinaataangGulay.setOnClickListener {
            val intent = Intent(this, GinaataangGulay::class.java)
            startActivity(intent)
        }

        Humba = findViewById(R.id.humba)
        Humba.setOnClickListener {
            val intent = Intent(this, Humba::class.java)
            startActivity(intent)
        }

        SinigangBangus = findViewById(R.id.sinigangBangus)
        SinigangBangus.setOnClickListener {
            val intent = Intent(this, SinigangBangus::class.java)
            startActivity(intent)
        }

        PataPorkBeans = findViewById(R.id.pata)
        PataPorkBeans.setOnClickListener {
            val intent = Intent(this, PataPorkBeans::class.java)
            startActivity(intent)
        }

        BicolExpress = findViewById(R.id.bicol)
        BicolExpress.setOnClickListener {
            val intent = Intent(this, BicolExpress::class.java)
            startActivity(intent)
        }

        Patotin = findViewById(R.id.patotin)
        Patotin.setOnClickListener {
            val intent = Intent(this, Patotin::class.java)
            startActivity(intent)
        }

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

    }
}