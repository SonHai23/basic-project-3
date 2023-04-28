package com.example.basicproject3.data.model

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class User(
    /*val id: String,
    val name: String,
    val email: String,
    val password: String,
    val address: String,
    val city: String,
    val dob: String,
    val phone: String,
    val image: String,
    val role: String*/
) {
    private lateinit var auth: FirebaseAuth
    fun getUsername(): Task<DocumentSnapshot> {
        val data = Firebase.firestore
        val uid = auth.currentUser?.uid
        val docRef = data.collection("users").document(uid!!)

        /*docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener() { exception ->
                Log.d(TAG, "get failed with ", exception)
            }*/
        return docRef.get()
    }
}