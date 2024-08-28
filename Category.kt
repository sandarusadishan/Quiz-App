package com.example.quiseapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Category : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val chem= findViewById<Button>(R.id.S1)
        chem.setOnClickListener {
            val intent = Intent(this, Chemistry::class.java)
            startActivity(intent)
        }
        val phy = findViewById<Button>(R.id.S2)
        phy.setOnClickListener {
            val intent = Intent(this,Physics ::class.java)
            startActivity(intent)
        }
        val bio = findViewById<Button>(R.id.S3)
        bio.setOnClickListener {
            val intent = Intent(this,Biology ::class.java)
            startActivity(intent)
        }
        val gen = findViewById<Button>(R.id.S4)
        gen.setOnClickListener {
            val intent = Intent(this,MainActivity ::class.java)
            startActivity(intent)
        }
        val image = findViewById<ImageView>(R.id.imagebtn1)
        image.setOnClickListener {
            val intent = Intent(this,Setting ::class.java)
            startActivity(intent)
        }



    }
}