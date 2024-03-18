package com.example.projectmanagment.SplashScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.example.projectmanagment.MainActivity
import com.example.projectmanagment.R
import com.example.projectmanagment.UserData.Register
import com.example.projectmanagment.UserData.Welcome


class SplashFragment_2 : Fragment(R.layout.fragment_splash_2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonToActivity = view.findViewById<TextView>(R.id.btn_splsh_second)
        buttonToActivity.setOnClickListener {
            val intent = Intent(context, Welcome::class.java)

            startActivity(intent)
        }
    }





}