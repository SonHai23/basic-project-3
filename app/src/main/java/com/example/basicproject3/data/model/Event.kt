package com.example.basicproject3.data.model

import com.google.firebase.Timestamp

data class Event(
    val id: String? = null,
    val host: String? = null,
    val title: String? = null,
    val description: String? = null,
    val location: String? = null,
    val age_of_restrict: String? = null,
    val ticket_price: String? = null,
    val date_created: Timestamp? = null,
    val date_start: Timestamp? = null,
    val date_end: Timestamp? = null
)