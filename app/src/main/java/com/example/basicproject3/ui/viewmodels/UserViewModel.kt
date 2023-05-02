package com.example.basicproject3.ui.viewmodels

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicproject3.R
import com.example.basicproject3.data.model.Image
import com.example.basicproject3.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class UserViewModel : ViewModel() {
//    private lateinit var auth: FirebaseAuth
//    private val auth = FirebaseAuth.getInstance()
    private val username = User()

    data class UserInformation(
        val name: String,
        val email: String
    )

    private val _profileInformation = MutableLiveData<UserInformation>().apply {
        /*val data = Firebase.firestore
        val uid = auth.currentUser?.uid
        val docRef = data.collection("users").document(uid!!)*/

        username.getUsername().addOnSuccessListener {
            if (it != null) {
//                    val userProfile = document.getString("name")
//                    val userProfile = it.data?.("name").toString()
                val userProfile = it.data?.get("name").toString()
                val userEmailProfile = it.data?.get("email").toString()

                val profile = UserInformation(userProfile, userEmailProfile)
                value = profile
            } else {
                Log.d(TAG, "No such document")
            }
        }
            .addOnFailureListener() { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    private val _profileAvatar = MutableLiveData<String>().apply {
        username.getAvatar().addOnSuccessListener {
            val imageUrl = it.toString()
            value = imageUrl
//            value = Picasso.get().load(imageUrl).placeholder(R.drawable.avatar_profile_default).into(imageView)
        }
            .addOnFailureListener() { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
    val profileInformation: LiveData<UserInformation> = _profileInformation
    val profileAvatar: LiveData<String> = _profileAvatar
}