package com.example.basicproject3.data.model

import android.net.Uri
import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.FirebaseStorage
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    @DocumentId val id: String? = null,
    val host: String? = null,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
    val location: String? = null,
    val date_start: Timestamp? = null,
    val date_end: Timestamp? = null
) : Parcelable {

    fun getImgUrl(): Task<Uri> {
        val storage = FirebaseStorage.getInstance()
        val imgRef = storage.getReference("events/$id")
        return imgRef.downloadUrl
    }
}