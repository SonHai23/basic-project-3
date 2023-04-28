package com.example.basicproject3.ui.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.User
import com.google.firebase.auth.FirebaseAuth

class UserViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth

    private val username = User()
    val uid = auth.currentUser?.uid

    private val _text = MutableLiveData<String>().apply {
        username.getUsername()
            .addOnSuccessListener { document ->
                if (document != null) {
//                    val userProfile = document.getString("name")
                    val userProfile = document.data!!["name"].toString()
                    value = userProfile
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener() { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
    val text: LiveData<String> = _text
}