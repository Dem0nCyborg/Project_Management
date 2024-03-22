package com.example.projectmanagment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.File

// FilesGridAdapter.kt
class FilesGridAdapter(private val context: Context, private val files: List<File>) : BaseAdapter() {

    override fun getCount(): Int {
        return files.size
    }

    override fun getItem(position: Int): Any {
        return files[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_layout, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val file = files[position]

        // Bind data to views
        holder.fileNameTextView.text = file.name
        val filetype: String = getFileType(file.name)
        // Set appropriate icon based on file type
        holder.iconImageView.setImageResource(getFileTypeIcon(filetype))

        return view
    }

    private fun getFileTypeIcon(fileType: String): Int {
        when(fileType) {
            "PDF" -> return R.drawable.ic_pdf
            "Image" -> return R.drawable.ic_pdf
            "Video" -> return R.drawable.ic_pdf
            "Text" -> return R.drawable.ic_pdf
            else -> return R.drawable.ic_pdf
        }
    }

    private fun getFileType(fileName: String): String {
        val extension = fileName.substringAfterLast('.')
        return when (extension.toLowerCase()) {
            "pdf" -> "PDF"
            "png", "jpg", "jpeg" -> "Image"
            "mp4", "avi", "mov" -> "Video"
            "txt" -> "Text"
            else -> "Unknown"
        }
    }

    private class ViewHolder(view: View) {
        val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
        val fileNameTextView: TextView = view.findViewById(R.id.fileNameTextView)
    }
}
