package com.example.collabnook.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.collabnook.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_create_project.*
import kotlinx.coroutines.NonCancellable.cancel


class CreateProjectFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the project_list_item for this fragment
        return inflater.inflate(R.layout.fragment_create_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.createProject).setOnClickListener {

            if (title.text.toString().isNullOrBlank() || desc.text.toString().isNullOrEmpty() ||
                domain.text.toString().isNullOrEmpty() || tech.text.toString().isNullOrEmpty() ||
                collabReq.text.toString().isNullOrEmpty()
            ) {
                Snackbar.make(view, "All fields are required.", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                addProject(
                    view, title.text.toString(), desc.text.toString(),
                    domain.text.toString(), tech.text.toString(), collabReq.text.toString()
                )
            }
        }
    }

    private fun addProject(
        view: View,
        title: String,
        desc: String,
        domain: String,
        tech: String,
        collabReq: String
    ) {
        val db = FirebaseFirestore.getInstance()

        val noteDataMap: MutableMap<String, Any> = HashMap()
        noteDataMap["title"] = title
        noteDataMap["description"] = desc
        noteDataMap["domain"] = domain
        noteDataMap["technology"] = tech
        noteDataMap["collaboratorRequirements"] = collabReq
        noteDataMap["createdBy"] = this.auth.currentUser.toString()

        // auto-generate ID
        val noteDocRef = db.collection("projects").document()
        noteDocRef.set(noteDataMap).addOnSuccessListener(OnSuccessListener<Void> {
            Log.d(TAG, "DocumentSnapshot successfully written!")
            findNavController().navigate(R.id.action_createProjectFragment_to_dashboardFragment)
        })
            .addOnFailureListener(OnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
            })

    }
}

