package com.example.basicproject3.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    @DocumentId var id: String? = null,
    val uid: String? = null,
    val date_purchased: Timestamp? = null
) : Parcelable {
}