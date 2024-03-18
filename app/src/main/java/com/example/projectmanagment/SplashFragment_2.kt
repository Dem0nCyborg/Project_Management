package com.example.projectmanagment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class SplashFragment_2 : Fragment(R.layout.fragment_splash_2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonToActivity = view.findViewById<TextView>(R.id.btn_splsh_second)
        buttonToActivity.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)

            startActivity(intent)
        }
    }





}