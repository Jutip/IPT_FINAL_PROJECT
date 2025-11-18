package com.example.ipt_final_project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import java.io.FileNotFoundException

class UploadRecipes : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val recipeCollection = db.collection("recipes")
    private val storage = FirebaseStorage.getInstance()

    private lateinit var titleInput: TextInputEditText
    private lateinit var ingredientsInput: TextInputEditText
    private lateinit var instructionsInput: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private lateinit var recipeImageView: ImageView
    private lateinit var addImageButton: Button
    private var currentRecipe: Recipe? = null
    private var selectedImageUri: Uri? = null
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private fun showSnack(message: String) {
       val snack = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
        snack.show()
        snack.view.postDelayed({
            snack.dismiss()
        }, 1500)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_recipes)

        titleInput = findViewById(R.id.edit_text_title)
        ingredientsInput = findViewById(R.id.edit_text_ingredients)
        instructionsInput = findViewById(R.id.edit_text_instructions)
        saveButton = findViewById(R.id.save_button)
        deleteButton = findViewById(R.id.delete_button)
        recipeImageView = findViewById(R.id.view_image)
        addImageButton = findViewById(R.id.add_button)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_add_edit)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupActivityLaunchers()

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

        addImageButton.setOnClickListener { checkPermissionAndOpenGallery() }
        saveButton.setOnClickListener { saveRecipe() }
        deleteButton.setOnClickListener { deleteRecipe() }
    }

    private fun setupActivityLaunchers() {
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK && result.data != null) {
                    selectedImageUri = result.data?.data
                    Glide.with(this)
                        .load(selectedImageUri)
                        .placeholder(R.drawable.img_placeholder)
                        .into(recipeImageView)
                }
            }

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    openGallery()
                } else {
                    showSnack("Permission denied to read storage")
                }
            }
    }

    private fun checkPermissionAndOpenGallery() {
        val permission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
            else Manifest.permission.READ_EXTERNAL_STORAGE

        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED ->
                openGallery()

            else -> requestPermissionLauncher.launch(permission)
        }
    }

    private fun openGallery() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun fillUiWithRecipeData() {
        currentRecipe?.let {
            titleInput.setText(it.title)
            ingredientsInput.setText(it.ingredients)
            instructionsInput.setText(it.instructions)
            deleteButton.visibility = View.VISIBLE

            if (!it.imageUrl.isNullOrEmpty()) {
                Glide.with(this)
                    .load(it.imageUrl)
                    .placeholder(R.drawable.img_placeholder)
                    .into(recipeImageView)
            }
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

        if (selectedImageUri != null) {
            uploadImageAndSaveRecipe(title, ingredients, instructions)
        } else {
            saveRecipeToFirestore(title, ingredients, instructions, currentRecipe?.imageUrl)
        }
    }

    private fun uploadImageAndSaveRecipe(
        title: String,
        ingredients: String,
        instructions: String
    ) {
        val filename = UUID.randomUUID().toString()
        val storageRef = storage.reference.child("recipe_images/$filename")

        selectedImageUri?.let { uri ->
            try {
                contentResolver.openInputStream(uri)?.close()
                storageRef.putFile(uri)
                    .addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            saveRecipeToFirestore(
                                title,
                                ingredients,
                                instructions,
                                downloadUri.toString()
                            )
                        }
                    }
                    .addOnFailureListener { e ->
                        showSnack("Image upload failed: ${e.message}")
                        saveButton.isEnabled = true
                    }
            } catch (e: FileNotFoundException) {
                showSnack("Error: Selected image not found. Please pick another.")
                saveButton.isEnabled = true
            } catch (e: Exception) {
                showSnack("Error: ${e.message}")
                saveButton.isEnabled = true
            }
        }
    }

    private fun saveRecipeToFirestore(
        title: String,
        ingredients: String,
        instructions: String,
        imageUrl: String?
    ) {
        val recipeToSave = currentRecipe?.apply {
            this.title = title
            this.ingredients = ingredients
            this.instructions = instructions
            this.imageUrl = imageUrl ?: this.imageUrl
        }  ?: Recipe(
            id = null,
            title = title,
            ingredients = ingredients,
            instructions = instructions,
            imageUrl = imageUrl
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

            currentRecipe?.imageUrl?.let { url ->
                if (url.isNotEmpty() && url.startsWith("https://firebasestorage.googleapis.com")) {
                    try {
                        storage.getReferenceFromUrl(url).delete()
                    } catch (e: Exception) {
                        Log.e("UploadRecipes", "Error deleting image from Storage: ${e.message}")
                    }
                }
            }

            recipeCollection.document(id).delete()
                .addOnSuccessListener {
                    showSnack("Recipe deleted")
                    finish()
                }
                .addOnFailureListener { e ->
                    showSnack("Error: ${e.message}")
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
