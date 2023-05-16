package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.Event
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EventViewModel : ViewModel() {

    private val db = Firebase.firestore

    suspend fun getEvent(category: String, id: String): Event {
        val event = db.collection("categories/$category/events").document(id).get().await()
        return Event(
            event.id,
            event.data!!["host"] as String,
            event.data!!["title"] as String,
            event.data!!["description"] as String,
            event.data!!["location"] as String,
            event.data!!["age_of_restrict"] as String,
            event.data!!["ticket_price"] as String,
            event.data!!["date_created"] as Timestamp,
            event.data!!["date_start"] as Timestamp,
            event.data!!["date_end"] as Timestamp
        )
    }
}