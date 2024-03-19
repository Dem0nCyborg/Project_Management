package com.example.projectmanagment.Conference

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmanagment.R
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

    }

    fun addFragment() {
        val appID : Long = 1724648990
        val appSign: String = "5b5cfa238e3b7b3e4a544579cb4cf826a76a8710a9f876a8c5d81cdff13fe790"

        val conferenceID = 123456
        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userID = sharedPreferences.getString("userID", "")!!.toInt()
        val userName = "Chandan"

        val config = ZegoUIKitPrebuiltVideoConferenceConfig()
        val fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign,
            userID.toString(), userName, conferenceID.toString(), config)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }


}