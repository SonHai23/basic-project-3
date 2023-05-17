package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.Category
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SearchViewModel : ViewModel() {

    private val db = Firebase.firestore

    suspend fun getCategoryList(): MutableList<Category> {
        val categoryList = mutableListOf<Category>()
        val querySnapshot = db.collection("categories").get().await()
        for (document in querySnapshot) {
//            val category = document.toObject<Category>()
//            categoryList.add(category)
            categoryList.add(Category(document.data["name"] as String))
        }
        return categoryList
    }

//    suspend fun getEventsByName(): MutableList<Event> {
//        val eventList = mutableListOf<Event>()
//        val querySnapshot = db.collectionGroup("events").get().await()
//        for (document in querySnapshot) {
//            eventList.add(Event(id = document.id, title = document.data["title"] as String?))
//        }
//        return eventList
//    }
}