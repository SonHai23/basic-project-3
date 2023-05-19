package com.example.basicproject3.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    @DocumentId var id: String? = null,
    val uid: String? = null,
    val eid: String? = null,
    val date_purchased: Timestamp? = null
) : Parcelable {

    @IgnoredOnParcel
    var event: Event? = null

    suspend fun getEvent(): Event? {
        val db = FirebaseFirestore.getInstance()
        val event = db.collection("events").document(eid!!).get().await()
        return event.toObject<Event>()
    }
}