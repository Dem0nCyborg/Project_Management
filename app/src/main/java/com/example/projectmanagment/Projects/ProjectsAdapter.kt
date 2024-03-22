package com.example.projectmanagment.Projects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagment.R

class ProjectsAdapter(private val projects: List<Projects>) : RecyclerView.Adapter<ProjectsAdapter.MyViewHolder>() {

    class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        val tvProjectTitle : TextView = itemView.findViewById(R.id.tv_project_name)
        val tvProjectCode : TextView = itemView.findViewById(R.id.tv_project_code)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.project_list_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = projects[position]
        holder.tvProjectTitle.text = currentItem.projectTitle
        holder.tvProjectCode.text = currentItem.projectCode.toString()
    }

}