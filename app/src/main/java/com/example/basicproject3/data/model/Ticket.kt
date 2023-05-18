package com.example.basicproject3.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    @DocumentId val id: String? = null,
    val uid: String,
    val date_purchased: Timestamp
) : Parcelable {
}