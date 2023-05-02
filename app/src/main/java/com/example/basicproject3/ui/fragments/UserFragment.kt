package com.example.basicproject3.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.basicproject3.HomeActivity
import com.example.basicproject3.R
import com.example.basicproject3.databinding.FragmentUserBinding
import com.example.basicproject3.ui.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val txtUserProfile: TextView = binding.txtUsername
        userViewModel.text.observe(viewLifecycleOwner) {
            txtUserProfile.text = it.name
        }

        val txtUserEmailProfile: TextView = binding.txtUserEmail
        userViewModel.text.observe(viewLifecycleOwner) {
            txtUserEmailProfile.text = it.email
        }

        // Get data from firestore
        /*val data = Firebase.firestore
        val uid = auth.currentUser?.uid
        val docRef = data.collection("users").document(uid!!)

        docRef.get().addOnSuccessListener {
            if (it != null) {
                val userProfile = it.data?.get("name").toString()
                val userEmailProfile = it.data?.get("email").toString()

                binding.txtUsername.text = userProfile
                binding.txtUserEmail.text = userEmailProfile
            } else {
                Log.d(ContentValues.TAG, "No such document")
            }
        }
            .addOnFailureListener() { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }*/

        /*val user = Firebase.auth.currentUser

        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
        }*/

        binding.txtAccountSetting.setOnClickListener {
            findNavController().navigate(R.id.navigate_to_change_profile_fragment)
        }

        binding.txtSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}