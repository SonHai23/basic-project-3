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
    private val auth = FirebaseAuth.getInstance()

    fun getUsername(): Task<DocumentSnapshot> {
        val db = Firebase.firestore
        val uid = auth.currentUser?.uid
        val docRef = db.collection("users").document(uid!!)

        return docRef.get()
    }
}