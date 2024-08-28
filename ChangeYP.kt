package com.example.quiseapp

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangeYP : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_yp)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Get references to the UI elements
        val oldPasswordEditText: EditText = findViewById(R.id.newtext1)
        val newPasswordEditText: EditText = findViewById(R.id.textnew2)
        val changePasswordButton: Button = findViewById(R.id.button6)

        // Load animations
        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)

        // Apply animations
        oldPasswordEditText.startAnimation(slideInAnimation)
        newPasswordEditText.startAnimation(slideInAnimation)
        changePasswordButton.startAnimation(slideInAnimation)

        changePasswordButton.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()

            if (oldPassword.isNotEmpty() && newPassword.isNotEmpty()) {
                changePassword(oldPassword, newPassword)
            } else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        val user = auth.currentUser
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)
            user.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
                            finish() // Close the activity and return to the previous one
                        } else {
                            Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Re-authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "No user is currently signed in", Toast.LENGTH_SHORT).show()
        }
    }
}
