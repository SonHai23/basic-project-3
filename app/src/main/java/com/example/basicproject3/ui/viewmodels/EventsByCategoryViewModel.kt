package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.Event
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EventsByCategoryViewModel : ViewModel() {

    private val db = Firebase.firestore

    suspend fun getEventListByCategory(category: String): MutableList<Event> {
        val eventList = mutableListOf<Event>()

        //lấy về danh sách event liên quan đến category
        val querySnapshot =
            db.collection("events").whereEqualTo("category", category).get().await()
        for (document in querySnapshot) {
            eventList.add(document.toObject(Event::class.java))
        }
        return eventList
    }

}