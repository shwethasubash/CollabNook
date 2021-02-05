package com.example.collabnook.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.collabnook.Projects
import com.example.collabnook.R
import com.google.firebase.firestore.FirebaseFirestore

class ProjectRecyclerAdaptor(
    private val projects: MutableList<Projects>,
    private val firestoreDB: FirebaseFirestore
    ) : RecyclerView.Adapter<ProjectRecyclerAdaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.project_list_item, parent, false)
        return ViewHolder(itemView)
    }



    override fun getItemCount(): Int {
        return projects.size
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val projectTitle = itemView?.findViewById<TextView>(R.id.projectTitleTextView)
        val projectDescription = itemView?.findViewById<TextView>(R.id.projectDescriptionTextView)
        val technology = itemView?.findViewById<TextView>(R.id.chip)
        val domain = itemView?.findViewById<TextView>(R.id.domain)
        var projectPosition = 0
    }


    override fun onBindViewHolder(holder: ProjectRecyclerAdaptor.ViewHolder, position: Int) {
        val project = projects[position]
        holder.projectTitle?.text = project.title
        holder.projectDescription?.text = project.description
        holder.technology?.text = project.technology
        holder.domain?.text = project.domain
        holder.projectPosition = position
    }

}