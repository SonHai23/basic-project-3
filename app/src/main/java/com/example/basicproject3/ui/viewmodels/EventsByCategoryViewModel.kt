package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.Event
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EventsByCategoryViewModel : ViewModel() {

    private val db = Firebase.firestore

    suspend fun getEventList(categoryName: String): MutableList<Event> {
        val eventList = mutableListOf<Event>()

        //lấy về danh sách event liên quan đến category
        val querySnapshot = db.collection("/categories/$categoryName/events").get().await()
        for (document in querySnapshot) {
            eventList.add(
                Event(
                    document.id,
                    document.data["host"] as String,
                    document.data["title"] as String
                )
            )
        }
        return eventList
    }
}