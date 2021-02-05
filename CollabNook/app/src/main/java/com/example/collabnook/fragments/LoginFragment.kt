package com.example.collabnook.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.collabnook.R
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.emailTextField
import kotlinx.android.synthetic.main.fragment_register.passwordTextField


class LoginFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.signUpButton).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {

            if (emailLogin.text.toString().isNullOrBlank() || passwordLogin.text.toString().isNullOrEmpty()) {
                Snackbar.make(view, "User or password should not be empty.", Snackbar.LENGTH_SHORT)
                    .show()
            }else {
                signIn(view,emailLogin.text.toString(), passwordLogin.text.toString())
            }
        }
    }

    private fun signIn(view: View,email: String, password: String) {
        Log.d(TAG, "signIn:$email")


        this.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //Registration OK
                val firebaseUser = this.auth.currentUser!!
                Log.d(TAG, "user: $firebaseUser")
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            } else {
                Log.d(TAG, "error****")
                Snackbar.make(view, "Credentials are incorrect. Try again!", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }


}