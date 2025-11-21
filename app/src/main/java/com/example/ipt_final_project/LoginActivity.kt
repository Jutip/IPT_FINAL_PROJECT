package com.example.ipt_final_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var isLoginMode = true
    private lateinit var auth: FirebaseAuth

    private lateinit var appTitle: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        appTitle = findViewById(R.id.app_title)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        loginButton = findViewById(R.id.login_button)
        signupLink = findViewById(R.id.signup_link)

        signupLink.setOnClickListener {
            toggleMode()
        }

        loginButton.setOnClickListener {
            handleAuthAction()
        }

        updateUI()
    }

    private fun toggleMode() {
        isLoginMode = !isLoginMode
        updateUI()
        emailEditText.text.clear()
        passwordEditText.text.clear()
        confirmPasswordEditText.text.clear()
    }

    private fun updateUI() {
        if (isLoginMode) {
            appTitle.text = "Welcome Back!"
            loginButton.text = "LOG IN"
            signupLink.text = "New user? Register Here"
            confirmPasswordEditText.visibility = View.GONE
        } else {
            appTitle.text = "Create Your Account"
            loginButton.text = "SIGN UP"
            signupLink.text = "Already have an account? Log In"
            confirmPasswordEditText.visibility = View.VISIBLE
        }
    }

    private fun handleAuthAction() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            return
        }

        if (isLoginMode) {

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, RecipePage::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                }

        } else {

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show()
                return
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account created! Logging in...", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, RecipePage::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}