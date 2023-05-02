package com.example.basicproject3.data.model

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class User() {
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val uid = auth.currentUser?.uid

    fun getUsername(): Task<DocumentSnapshot> {
        val db = Firebase.firestore
//        val uid = auth.currentUser?.uid
        val docRef = db.collection("users").document(uid!!)

        return docRef.get()
    }

    fun getAvatar(): Task<Uri> {
        val storageRef = storage.reference.child("profiles/${uid}")
        return storageRef.downloadUrl
    }
}