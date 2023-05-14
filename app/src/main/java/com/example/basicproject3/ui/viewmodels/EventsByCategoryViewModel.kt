package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.Event
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Date

class EventsByCategoryViewModel : ViewModel() {

    private val db = Firebase.firestore

    suspend fun getEventList(categoryName: String): MutableList<Event> {
        val eventList = mutableListOf<Event>()

        //lấy về danh sách user có tạo event liên quan đến category
        val documents =
            db.collection("categories").document(categoryName).get().await()

        for (collection in documents.data!!) {
            val querySnapshot = db.collection(collection.value.toString()).get().await()
            for (document in querySnapshot) {
                eventList.add(
                    Event(
                        document.id,
                        document.data["title"] as String,
                        document.data["description"] as String,
                        document.data["address"] as String,
                        document.data["city"] as String,
                        document.data["phone"] as String,
                        document.data["images"] as List<String>,
                        document.data["category"] as String,
                        document.data["age_of_restrict"] as Int,
                        document.data["ticket_price"] as Int,
                        document.data["ticket_quantity"] as Int,
                        document.data["date_start"] as Date,
                        document.data["date_end"] as Date
                    )
                )
            }
        }

//        documents.addOnSuccessListener {
//            for (collection in it.data!!) {
//                for (document in collection) {
//                    eventList.add(
//                        Event(
//                            document.id,
//                            document.data["title"] as String,
//                            document.data["description"] as String,
//                            document.data["address"] as String,
//                            document.data["city"] as String,
//                            document.data["phone"] as String,
//                            document.data["images"] as List<String>,
//                            document.data["category"] as String,
//                            document.data["age_of_restrict"] as Int,
//                            document.data["ticket_price"] as Int,
//                            document.data["ticket_quantity"] as Int,
//                            document.data["date_start"] as Date,
//                            document.data["date_end"] as Date
//                        )
//                    )
//                }
//            }
//        }

//        for (collection in docRef) {
//            val event = Event(
//                document.id,
//                document.data["title"] as String,
//                document.data["description"] as String,
//                document.data["address"] as String,
//                document.data["city"] as String,
//                document.data["phone"] as String,
//                document.data["images"] as List<String>,
//                document.data["category"] as String,
//                document.data["age_of_restrict"] as Int,
//                document.data["ticket_price"] as Int,
//                document.data["ticket_quantity"] as Int,
//                document.data["date_start"] as Date,
//                document.data["date_end"] as Date
//            )
//            eventList.add(event)
//        }
        return eventList
    }
}