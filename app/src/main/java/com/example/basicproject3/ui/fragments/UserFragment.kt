package com.example.basicproject3.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.basicproject3.MyEventActivity
import com.example.basicproject3.HomeActivity
import com.example.basicproject3.R
import com.example.basicproject3.databinding.FragmentUserBinding
import com.example.basicproject3.ui.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UserFragment : Fragment() {

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

        lifecycleScope.launch {
            userViewModel.profileAvatar.observe(viewLifecycleOwner) {
                // Su dung Glide de load image

                if (it != null) {
                    Glide.with(binding.fragmentUser).load(it).into(binding.imgAvatar)
                } else {
                    binding.imgAvatar.setImageResource(R.drawable.avatar_profile_default)
                }
            }

            val txtUserProfile: TextView = binding.txtUsername
            userViewModel.profileInformation.observe(viewLifecycleOwner) {
                txtUserProfile.text = it.name
            }

            val txtUserEmailProfile: TextView = binding.txtUserEmail
            userViewModel.profileInformation.observe(viewLifecycleOwner) {
                txtUserEmailProfile.text = it.email
            }
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

        binding.txtMyEvent.setOnClickListener {
            val intent = Intent(activity, MyEventActivity::class.java)
            startActivity(intent)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event to back to home fragment
            findNavController().navigate(R.id.action_navigation_user_to_navigation_home)
        }
        return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}