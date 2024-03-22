package com.example.projectmanagment.UserData

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmanagment.Projects.MainActivity
import com.example.projectmanagment.R
import com.example.projectmanagment.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlin.random.Random

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        firebaseAuth=FirebaseAuth.getInstance()



        binding.btnSignin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val username = binding.edUsername.text.toString()
            val password = binding.edPassword.text.toString()
            val confirmpassword = binding.edConfirmPassword.text.toString()
            register(email,username,password,confirmpassword,editor)
        }

    }

    private fun register(email: String, username: String, password: String, confirmpassword: String, editor: SharedPreferences.Editor?) {

        if(email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {

                editor!!.putString("username",username)
                editor.putString("email",email)
                editor.putString("password",password)
                editor.putString("userID", Random.nextInt(1000,9999).toString())
                editor.apply()
                startActivity(Intent(this, MainActivity::class.java))
            }
                .addOnFailureListener {
                    Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show()
                }
        }
        else{
            Toast.makeText(this,"Please all the Information", Toast.LENGTH_SHORT).show()
        }

    }


}