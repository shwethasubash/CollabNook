package com.example.collabnook.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.collabnook.R
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_create_project.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.emailTextField
import kotlinx.android.synthetic.main.fragment_register.passwordTextField

class RegisterFragment : Fragment() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the project_list_item for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.logInButton).setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        view.findViewById<Button>(R.id.registerButton).setOnClickListener {
            if (emailTextField.text.toString().isNullOrBlank() || passwordTextField.text.toString().isNullOrEmpty() || name.text.toString().isNullOrEmpty() ||
                   confirmPassword.text.toString().isNullOrBlank() ) {
                Snackbar.make(view, "All fields are required.", Snackbar.LENGTH_SHORT)
                    .show()
            }else {

                if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailTextField.text.toString()).matches()) {

                    if (passwordTextField.text.toString()
                             == confirmPassword.text.toString()
                    ) {
                        createAccount(
                            emailTextField.text.toString(),
                            passwordTextField.text.toString()
                        )
                    }
                    else {
                        Snackbar.make(view, "Password and confirm password values do not match. Please try again!", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
                else {
                    Snackbar.make(view, " Please enter valid email.", Snackbar.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }


    private fun createAccount(email: String, password: String) {

        Log.d(TAG, "createAccount:$email")

        this.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                val firebaseUser = this.auth.currentUser!!
                Log.d(TAG, "user: $firebaseUser")
                findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment)
            } else {
                Log.d(TAG, "error****")
            }
        }
    }




}