package com.example.basicproject3.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.Category
import com.example.basicproject3.data.model.OrganizersToFollow
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class HomeViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val uid = auth.currentUser?.uid

    /*private val _organizerInformation = MutableLiveData<String>().apply {
        val db = Firebase.firestore

        db.collection("users").document(uid!!).get()
            .addOnSuccessListener {
                if (it != null) {
                    val organizerName = it.data?.get("name").toString()

                    value = organizerName
                }
        }
    }

    private val _organizerAvatar = MutableLiveData<String>().apply {
        storage.reference.child("profiles/${uid}").downloadUrl
            .addOnSuccessListener {
                value = it.toString()
            }
    }
    val organizerInformation: LiveData<String> = _organizerInformation
    val organizerAvatar: LiveData<String> = _organizerAvatar*/

    /*private val db = Firebase.firestore

    suspend fun getOrganizerList(): MutableList<OrganizersToFollow> {
        val organizerList = mutableListOf<OrganizersToFollow>()
        val querySnapshot = db.collection("users").document().get().await()
        *//*for (document in querySnapshot) {
            organizerList.add(OrganizersToFollow(document.id))
        }*//*
        organizerList.add(OrganizersToFollow(querySnapshot.toString()))

        return organizerList
    }*/
}