package com.example.projectmanagment.Projects

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmanagment.GlobalData
import com.example.projectmanagment.R
import com.example.projectmanagment.Storage.SharedData
import com.example.projectmanagment.UserData.Login
import com.example.projectmanagment.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var projectsArrayList : ArrayList<Projects>
    private lateinit var projName : ArrayList<String>
    private lateinit var projId : ArrayList<Int>


    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var uid: String

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", MODE_PRIVATE)


        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid ?: ""



        binding.btnLogout.setOnClickListener {
            signOut()
            navigateToLogin()
        }


        projectsArrayList = ArrayList()
        projName = ArrayList()
        projId = ArrayList()




        binding.rvProjects.layoutManager = LinearLayoutManager(this)
        binding.rvProjects.setHasFixedSize(true)
        binding.rvProjects.adapter = ProjectsAdapter(projectsArrayList)
        getdata()


        binding.btnAdd.setOnClickListener {
            onAddButtonClicked()
        }

        binding.btnAddProject1.setOnClickListener {
            binding.cvCreateProject.visibility = View.VISIBLE
            Toast.makeText(this, "Add Project", Toast.LENGTH_SHORT).show()
            toAddProject()
        }

        binding.btnJoinProject1.setOnClickListener {
            toJoinProject()
        }


    }



    private fun getdata() {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(uid)

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                projectsArrayList.clear()

                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {

                    for (projectSnapshot in dataSnapshot.children) {

                        val projName =
                            projectSnapshot.child("projectTitle").getValue(String::class.java)
                        val projCode =
                            projectSnapshot.child("projectCode").getValue(Int::class.java)
                        if (projName != null && projCode != null) {
                            projectsArrayList.add(Projects(projName, projCode))

                        }


                    }
                    val adapter = ProjectsAdapter(projectsArrayList)
                    binding.rvProjects.adapter = adapter
                    adapter.notifyDataSetChanged()
                } else {
                    println("No projects found.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
                println("Database Error: ${databaseError.message}")
            }
        })

    }

    private fun toJoinProject() {
        clicked = !clicked
        if (!clicked) {
            binding.cvJoinProject.isClickable = true
            binding.cvJoinProject.visibility = View.VISIBLE

            binding.btnJoin2.setOnClickListener {
                if (binding.tvProjectCode2.text.toString().isNotEmpty()) {
                    GlobalData.projectCode = binding.tvProjectCode2.text.toString().toInt()
                    binding.cvJoinProject.visibility = View.INVISIBLE
                    binding.tvProjectCode2.text?.clear()
                    binding.cvJoinProject.isClickable = false
                    startActivity(Intent(this, SharedData::class.java))
                } else {
                    Toast.makeText(this, "Please enter a project ID", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            binding.cvJoinProject.isClickable = false
            binding.cvJoinProject.visibility = View.INVISIBLE
        }
    }

    private fun toAddProject() {
        binding.cvCreateProject.visibility = View.VISIBLE
        clicked = !clicked
        if (!clicked){
            binding.btnCreateProject2.setOnClickListener {
                if (binding.tvProjectTitle.text.toString().isNotEmpty()  ) {
                    val random : Random = Random
                    val randomNumber = random.nextInt(9999) // You can adjust the range of random numbers as needed
                    val projName = binding.tvProjectTitle.text.toString()
                    val projCode = randomNumber
                    val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("projCode", projCode.toString())
                    database.getReference(uid).child(uid+projCode).setValue(Projects(projName, projCode)).addOnSuccessListener {
                        getdata()
                        binding.cvCreateProject.visibility = View.GONE
                        binding.tvProjectTitle.text?.clear()
                        binding.tvProjectTitle.visibility = View.GONE
                    }
                    editor.apply()







                } else {
                    Toast.makeText(this, "Please enter a project name", Toast.LENGTH_SHORT).show()
                }

            }
        } else {
            binding.cvCreateProject.isClickable = false
            binding.cvCreateProject.visibility = View.INVISIBLE
        }
    }





    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {

if (!clicked) {
            binding.btnAdd.startAnimation(rotateOpen)
            binding.btnAddProject1.startAnimation(fromBottom)
            binding.btnJoinProject1.startAnimation(fromBottom)
            binding.btnJoinProject1.isClickable = true
            binding.btnAddProject1.isClickable = true
        }
else {
            binding.btnAdd.startAnimation(rotateClose)
            binding.btnJoinProject1.startAnimation(toBottom)
            binding.btnAddProject1.startAnimation(toBottom)
            binding.btnJoinProject1.isClickable = false
            binding.btnAddProject1.isClickable = false
        }

    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.btnAddProject1.visibility = View.VISIBLE
            binding.btnJoinProject1.visibility = View.VISIBLE
        } else {
            binding.btnAddProject1.visibility = View.INVISIBLE
            binding.btnJoinProject1.visibility = View.INVISIBLE
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish() // Optional: Finish the current activity to prevent going back to it after signing out
    }


}