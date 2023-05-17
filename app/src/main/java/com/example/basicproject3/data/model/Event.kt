package com.example.basicproject3.data.model

import android.net.Uri
import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.FirebaseStorage
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    @DocumentId val id: String? = null,
    val host: String? = null,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
    val location: String? = null,
    val date_start: String? = null,
    val date_end: String? = null
) : Parcelable {
    @IgnoredOnParcel
    private val storage = FirebaseStorage.getInstance()

    fun getImgUrl(): Task<Uri> {
        val imgRef = storage.getReference("profiles/$id")
        return imgRef.downloadUrl
    }
}