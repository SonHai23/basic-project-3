package com.example.basicproject3.data.model

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.FirebaseStorage

data class Organizer(
    @DocumentId val id: String? = null,
    val name: String? = null
) {
    fun getProfileAvatar(): Task<Uri> {
        val storage = FirebaseStorage.getInstance()
        val imgRef = storage.getReference("profiles/$id")
        return imgRef.downloadUrl
    }
}