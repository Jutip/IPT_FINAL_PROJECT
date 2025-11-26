package com.example.ipt_final_project

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class UploadRecipes : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val recipeCollection = db.collection("recipes")

    private lateinit var titleInput: TextInputEditText
    private lateinit var ingredientsInput: TextInputEditText
    private lateinit var instructionsInput: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private var currentRecipe: Recipe? = null

    private fun showSnack(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_recipes)

        titleInput = findViewById(R.id.edit_text_title)
        ingredientsInput = findViewById(R.id.edit_text_ingredients)
        instructionsInput = findViewById(R.id.edit_text_instructions)
        saveButton = findViewById(R.id.save_button)
        deleteButton = findViewById(R.id.delete_button)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_add_edit)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra("RECIPE_DATA")) {
            currentRecipe =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("RECIPE_DATA", Recipe::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra("RECIPE_DATA")
                }

            supportActionBar?.title = "Edit Recipe"
            fillUiWithRecipeData()
        } else {
            supportActionBar?.title = "Add New Recipe"
            deleteButton.visibility = View.GONE
        }

        saveButton.setOnClickListener { saveRecipe() }
        deleteButton.setOnClickListener { deleteRecipe() }
    }

    private fun fillUiWithRecipeData() {
        currentRecipe?.let {
            titleInput.setText(it.title)
            ingredientsInput.setText(it.ingredients)
            instructionsInput.setText(it.instructions)
            deleteButton.visibility = View.VISIBLE
        }
    }

    private fun saveRecipe() {
        val title = titleInput.text.toString().trim()
        val ingredients = ingredientsInput.text.toString().trim()
        val instructions = instructionsInput.text.toString().trim()

        if (title.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            showSnack("Please fill out all fields")
            return
        }

        saveButton.isEnabled = false

        saveRecipeToFirestore(title, ingredients, instructions)
    }

    private fun saveRecipeToFirestore(
        title: String,
        ingredients: String,
        instructions: String
    ) {
        val recipeToSave = currentRecipe?.copy(
            title = title,
            ingredients = ingredients,
            instructions = instructions
        ) ?: Recipe(
            id = null,
            title = title,
            ingredients = ingredients,
            instructions = instructions,
            imageUrl = null
        )

        if (recipeToSave.id == null) {
            recipeCollection.add(recipeToSave)
                .addOnSuccessListener {
                    showSnack("Recipe saved!")
                    finish()
                }
                .addOnFailureListener { e ->
                    showSnack("Error: ${e.message}")
                    saveButton.isEnabled = true
                }
        } else {
            recipeCollection.document(recipeToSave.id!!).set(recipeToSave)
                .addOnSuccessListener {
                    showSnack("Recipe updated!")
                    finish()
                }
                .addOnFailureListener { e ->
                    showSnack("Error: ${e.message}")
                    saveButton.isEnabled = true
                }
        }
    }

    private fun deleteRecipe() {
        currentRecipe?.id?.let { id ->
            deleteButton.isEnabled = false

            recipeCollection.document(id).delete()
                .addOnSuccessListener {
                    showSnack("Recipe deleted")
                    finish()
                }
                .addOnFailureListener { e ->
                    showSnack("Error: ${e.message}")
                    deleteButton.isEnabled = true
                }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}