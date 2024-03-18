package com.example.projectmanagment.SplashScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.projectmanagment.R


class SplashFragment_1 : Fragment(R.layout.fragment_splash_1) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_splsh_first : View = view.findViewById(R.id.btn_splsh_first)
        btn_splsh_first.setOnClickListener {
            val fragmentTwo = SplashFragment_2()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.replace(R.id.splash_fragment_container, fragmentTwo)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


}