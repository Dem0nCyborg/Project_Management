package com.example.projectmanagment.Conference

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmanagment.R
import com.example.projectmanagment.Storage.SharedData
import com.example.projectmanagment.Todo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment

class Meet : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        addFragment()

        val btmnav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        btmnav.selectedItemId = R.id.item_2

        btmnav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    startActivity(Intent(this, SharedData::class.java))
                    finish()
                    true
                }
                R.id.item_2 -> {
                    Toast.makeText(this, "Already There", Toast.LENGTH_SHORT).show()

                    true
                }
                R.id.item_3 -> {
                    startActivity(Intent(this, Todo::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }


    }

    fun addFragment() {
        val appID : Long = 1724648990
        val appSign: String = "5b5cfa238e3b7b3e4a544579cb4cf826a76a8710a9f876a8c5d81cdff13fe790"

        val conferenceID = 123456
        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userID = 5454554
        val userName = "Chandan"

        val config = ZegoUIKitPrebuiltVideoConferenceConfig()
        val fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign,
            userID.toString(), userName, conferenceID.toString(), config)

        supportFragmentManager.beginTransaction()
            .replace(R.id.meetFragment, fragment)
            .addToBackStack("MeetFragment")
            .commit()

    }


}