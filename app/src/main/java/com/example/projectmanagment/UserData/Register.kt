package com.example.projectmanagment.UserData

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmanagment.R
import com.example.projectmanagment.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

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

        firebaseAuth=FirebaseAuth.getInstance()
        binding.btnReister.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val username = binding.edUsername.text.toString()
            val password = binding.edPassword.text.toString()
            val confirmpassword = binding.edConfirmPassword.text.toString()




            if(email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    Toast.makeText(this,"Registration Successfull",Toast.LENGTH_SHORT).show()
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
}