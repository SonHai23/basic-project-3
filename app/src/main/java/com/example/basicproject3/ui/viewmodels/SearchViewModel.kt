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
            categoryList.add(Category(document.id))
        }
        return categoryList
    }
}