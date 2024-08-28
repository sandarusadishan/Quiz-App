package com.example.quiseapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class Setting : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Change Password Button
        val changePasswordButton = findViewById<Button>(R.id.S20)
        changePasswordButton.setOnClickListener {
            val intent = Intent(this, ChangeYP::class.java)
            startActivity(intent)
        }

        // Delete Account Button
        val deleteAccountButton = findViewById<Button>(R.id.S10)
        deleteAccountButton.setOnClickListener {
            showPasswordDialog()
        }
    }

    private fun showPasswordDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.activity_delete_account, null)
        dialogBuilder.setView(dialogView)

        val passwordField: EditText = dialogView.findViewById(R.id.password)

        dialogBuilder.setTitle("Delete Account")
        dialogBuilder.setPositiveButton("Delete") { _, _ ->
            val password = passwordField.text.toString()
            if (password.isNotEmpty()) {
                deleteAccount(password)
            } else {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun deleteAccount(password: String) {
        val user = auth.currentUser
        if (user != null) {
            val email = user.email

            if (email != null) {
                val credential = EmailAuthProvider.getCredential(email, password)
                user.reauthenticate(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Delete the user account
                        user.delete().addOnCompleteListener { deleteTask ->
                            if (deleteTask.isSuccessful) {
                                Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                                // Navigate to the settings page
                                val intent = Intent(this, LoginIntroActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Failed to delete account", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Re-authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "User email not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No user is currently signed in", Toast.LENGTH_SHORT).show()
        }
    }
}
