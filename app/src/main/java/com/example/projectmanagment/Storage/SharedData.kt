package com.example.projectmanagment.Storage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmanagment.Conference.Meet
import com.example.projectmanagment.FilesGridAdapter
import com.example.projectmanagment.R
import com.example.projectmanagment.Todo
import com.example.projectmanagment.databinding.ActivitySharedDataBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class SharedData : AppCompatActivity() {

    lateinit var binding: ActivitySharedDataBinding


    val FILE_REQUEST_CODE = 100 // Request code for file selection
    lateinit var uri: Uri
    lateinit var mStorage: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySharedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        mStorage = FirebaseStorage.getInstance().getReference("Uploads")

        val selectFileBtn=binding.selectFileBtn
        selectFileBtn.setOnClickListener {
            selectFile()
        }
        retrieveFiles()
        binding.bottomNavigation.selectedItemId = R.id.item_1

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item_2 -> {
                    startActivity(Intent(this, Meet::class.java))
                    finish()
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

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Accept all file types
        startActivityForResult(Intent.createChooser(intent, "Select File"), FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == FILE_REQUEST_CODE) {
            // File selected successfully
            uri = data?.data ?: return
            val uriTxt = findViewById<TextView>(R.id.uriTxt)
            uriTxt.text = uri.toString()
            uploadFile()
        }
    }

    private fun uploadFile() {
        val fileName = uri.lastPathSegment ?: return
        // Get SharedPreferences instance
        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val location = sharedPreferences.getString("projCode", "default_value")
        val fileReference = location?.let { mStorage.child(it).child(fileName) }
        try {
            if (fileReference != null) {
                fileReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
                    val downloadUrl = uri.toString()
                    val dwnTxt = findViewById<TextView>(R.id.dwnTxt)
                    dwnTxt.text = downloadUrl.toString()
                    Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun retrieveFiles() {
        // Get SharedPreferences instance
        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val location = sharedPreferences.getString("projCode", "default_value")
        if (location != null) {
            val folderReference = mStorage.child(location)

            folderReference.listAll().addOnSuccessListener { listResult ->
                val filesList1 = ArrayList<String>()
                for (item in listResult.items) {
                    val fileName = item.name // Get file name
                    filesList1.add(fileName)
                }
                // Populate grid view with file names
                // Assuming you have a GridView with id filesGridView in your layout
                val gridView = findViewById<GridView>(R.id.gridView)

// Sample list of files (you need to replace it with your actual data)
                val filesList = listOf<File>(File("file1.pdf"), File("file2.png"), File("file3.txt"))

                val adapter = FilesGridAdapter(this, filesList)
                gridView.adapter = adapter
            }.addOnFailureListener { exception ->
                // Handle any errors that may occur during retrieval
                Toast.makeText(this, "Failed to retrieve files: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}