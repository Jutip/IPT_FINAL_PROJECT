package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Locale

class MyRecipes : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val recipeCollection = db.collection("recipes")
    private var adapter: RecipeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_recipes)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_my_recipes)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "My Recipes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add_recipe)
        fabAdd.setOnClickListener {
            startActivity(Intent(this, UploadRecipes::class.java))
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val query = recipeCollection.orderBy("timestamp", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<Recipe>()
            .setQuery(query, Recipe::class.java)
            .build()

        adapter = RecipeAdapter(options)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_recipes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val recipeId = adapter?.snapshots?.getSnapshot(position)?.id

                if (recipeId != null) {
                    recipeCollection.document(recipeId).delete()
                        .addOnSuccessListener {
                            Toast.makeText(this@MyRecipes, "Recipe deleted", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }).attachToRecyclerView(recyclerView)
    }

    private class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.text_view_title)
        val timestamp: TextView = view.findViewById(R.id.text_view_timestamp)
    }
    private inner class RecipeAdapter(options: FirestoreRecyclerOptions<Recipe>) :
        FirestoreRecyclerAdapter<Recipe, RecipeViewHolder>(options) {

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_list, parent, false)
            return RecipeViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int, model: Recipe) {
            holder.title.text = model.title
            holder.timestamp.text = model.timestamp?.let { dateFormat.format(it) } ?: "No date"
            holder.itemView.setOnClickListener {

                val intent = Intent(this@MyRecipes , UploadRecipes::class.java)

                val recipeId = snapshots.getSnapshot(position).id
                model.id = recipeId
                intent.putExtra("RECIPE_DATA", model)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}