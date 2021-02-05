package com.example.collabnook.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.collabnook.Projects
import com.example.collabnook.R
import com.example.collabnook.adaptors.ProjectRecyclerAdaptor
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {


    private var adapter: ProjectRecyclerAdaptor? = null

   private var firestoreDB: FirebaseFirestore? = null
   private var firestoreListener: ListenerRegistration? = null


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestoreDB = FirebaseFirestore.getInstance()

         loadNotesList()

         /*firestoreListener = firestoreDB!!.collection("projects")
             .addSnapshotListener(EventListener { documentSnapshots, e ->
                 if (e != null) {
                     Log.e(TAG, "Listen failed!", e)
                     return@EventListener
                 }

                 val projectList = mutableListOf<Projects>()

                 if (documentSnapshots != null) {
                     for (doc in documentSnapshots) {
                         val note = doc.toObject(Projects::class.java)
                         note.id = doc.id
                         projectList.add(note)
                     }
                 }

                 this.adapter = ProjectRecyclerAdaptor(projectList, firestoreDB!!)
                 projectsListRecyclerView.adapter = adapter
//                 this.adapter!!.notifyDataSetChanged()
             })*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the project_list_item for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.createProjectButton).setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_createProjectFragment)
        }

        loadNotesList()


    }

    private fun loadNotesList() {
        firestoreDB!!.collection("projects")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val projectList = mutableListOf<Projects>()

                    for (doc in task.result!!) {

                        val project = doc.toObject<Projects>()
                        project.id = doc.id
                        projectList.add(project)
                    }

                    projectsListRecyclerView.layoutManager = LinearLayoutManager(activity)
                    this.adapter = ProjectRecyclerAdaptor(projectList, firestoreDB!!)
                    projectsListRecyclerView.adapter = adapter
                    projectsListRecyclerView.addItemDecoration(DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL));
//                    this.adapter!!.notifyDataSetChanged()
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

}