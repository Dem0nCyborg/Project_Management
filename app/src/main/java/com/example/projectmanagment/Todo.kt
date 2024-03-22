package com.example.projectmanagment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmanagment.Conference.Meet
import com.example.projectmanagment.Storage.SharedData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class Todo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_todo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btmnav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        btmnav.selectedItemId = R.id.item_3

        btmnav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    startActivity(Intent(this, SharedData::class.java))
                    finish()
                    true
                }
                R.id.item_2 -> {
                    startActivity(Intent(this, Meet::class.java))
                    finish()
                    true
                }
                R.id.item_3 -> {
                    Toast.makeText(this, "Already There", Toast.LENGTH_SHORT).show()

                    true
                }
                else -> false
            }
        }


        val floatbtn = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.floatingActionButton)

        floatbtn.setOnClickListener {
            val contextView = floatbtn
            Snackbar.make(contextView, "This is just for testing purposes..!", Snackbar.LENGTH_SHORT)
                .show()
        }


    }
}