package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.Organizer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EventViewModel() : ViewModel() {
    private var db = Firebase.firestore

    suspend fun getOrganizer(id: String): Organizer {
        val querySnapshot = db.collection("users").document(id).get().await()
        val organizer = querySnapshot.toObject(Organizer::class.java)
        return organizer!!
    }
}