package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView



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
        lateinit var pork: Button
        lateinit var fish: Button
        lateinit var beef: Button
        lateinit var duck: Button
        lateinit var vegetables: Button
        lateinit var desserts: Button
        lateinit var menuButton: ImageButton
        lateinit var drawerLayout: DrawerLayout
        lateinit var navigationView: NavigationView




        override fun onCreate(savedInstanceState: Bundle?) {
            enableEdgeToEdge()
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_recipe_page)

            val searchItems = listOf("Tinolang Manok", "Chicken Curry", "Chicken Inasal", "Pork Sinigang", "Pork Barbecue", "Lechon Kawali", "Bistek Tagalog", "Bulalo", "Beef Kare-Kare", "Rellenong Bangus", "Escabeche", "Grilled Fish", "Roasted Duck", "Duck Kaldereta", "Kinulob na Itik", "Chopsuey", "Pinakbet", "Tortang Talong", "Leche Flan", "Halo-Halo", "Buko Pandan")
            val searchAutoComplete = findViewById<AutoCompleteTextView>(R.id.searchAutoComplete)

            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, searchItems)
            searchAutoComplete.setAdapter(adapter)

            searchAutoComplete.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                when (selectedItem) {
                "Chicken Curry" -> startActivity(Intent(this, ChickenCurry::class.java))
                "Tinolang Manok" -> startActivity(Intent(this, TinolangManok::class.java))
                "Chicken Inasal" -> startActivity(Intent(this, ChickenInasal::class.java))
                "Pork Barbecue"  -> startActivity(Intent(this, Porkbbq::class.java))
                "Lechon Kawali" -> startActivity(Intent(this, LechonKawali::class.java))
                "Pork Sinigang" -> startActivity(Intent(this, PorkSinigang::class.java))
                "Bistek Tagalog" -> startActivity(Intent(this, BistekTagalog::class.java))
                "Beef Kare-Kare" -> startActivity(Intent(this, Beefkare::class.java))
                "Bulalo" -> startActivity(Intent(this, Bulalo::class.java))
                "Escabeche" -> startActivity(Intent(this, Escabeche::class.java))
                "Rellenong Bangus" -> startActivity(Intent(this, RellenongBangus::class.java))
                "Grilled Fish" -> startActivity(Intent(this, GrilledFish::class.java))
                "Roasted Duck" -> startActivity(Intent(this, RoastedDuck::class.java))
                "Duck Kaldereta" -> startActivity(Intent(this, DuckKaldereta::class.java))
                "Kinulob na Itik" -> startActivity(Intent(this, KinulobItik::class.java))
                "Chopsuey" -> startActivity(Intent(this, Chopsuey::class.java))
                "Pinakbet" -> startActivity(Intent(this, Pinakbet::class.java))
                "Tortang Talong" -> startActivity(Intent(this, TortangTalong::class.java))
                "Leche Flan" -> startActivity(Intent(this, LecheFlan::class.java))
                "Halo-Halo" -> startActivity(Intent(this, Halohalo::class.java))
                "Buko Pandan" -> startActivity(Intent(this, BukoPandan::class.java))
            }

                searchAutoComplete.clearFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(searchAutoComplete.windowToken, 0)
            }


            drawerLayout = findViewById(R.id.drawer_layout)
            val navigationView = findViewById<NavigationView>(R.id.nav_view_end)
            menuButton = findViewById(R.id.menubutton)

            menuButton.setOnClickListener {
                if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.openDrawer(GravityCompat.END)
                }
            }
                navigationView.setNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.my_recipes -> {
                            startActivity(Intent(this, MyRecipes::class.java))
                            true
                        }
                        else -> false
                    }
                }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }


        chicken = findViewById(R.id.chicken)
        chicken.setOnClickListener {
            val intent = Intent(this, Chicken::class.java)
            startActivity(intent)
        }

        pork = findViewById(R.id.pork)
        pork.setOnClickListener {
            val intent = Intent(this, Pork::class.java)
            startActivity(intent)
        }

        beef = findViewById(R.id.beef)
        beef.setOnClickListener {
            val intent = Intent(this, Beef::class.java)
            startActivity(intent)
        }

        fish = findViewById(R.id.fish)
        fish.setOnClickListener {
            val intent = Intent(this, Fish::class.java)
            startActivity(intent)
        }

        duck = findViewById(R.id.duck)
        duck.setOnClickListener {
            val intent = Intent(this, Duck::class.java)
            startActivity(intent)
        }

        vegetables = findViewById(R.id.vegetables)
        vegetables.setOnClickListener {
            val intent = Intent(this, Vegetables::class.java)
            startActivity(intent)
        }

        desserts = findViewById(R.id.button7)
        desserts.setOnClickListener {
            val intent = Intent(this, Dessert::class.java)
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
    }
}