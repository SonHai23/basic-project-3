package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.Category
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class SearchViewModel : ViewModel() {

    private var _categoryList = MutableLiveData<List<Category>>().apply {
        val db = Firebase.firestore
        val querySnapshot = db.collection("categories")
        querySnapshot.get().addOnSuccessListener {
            val categoryList = mutableListOf<Category>()
            for (document in it) {
                if (it != null) {
                    val category = document.toObject<Category>()
                    categoryList.add(category)
                }
            }
            value = categoryList
        }
    }

    val categoryList: LiveData<List<Category>> = _categoryList
}