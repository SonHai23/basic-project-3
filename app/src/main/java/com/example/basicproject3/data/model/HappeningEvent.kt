package com.example.basicproject3.data.model

import com.google.firebase.Timestamp
import java.util.Date

data class HappeningEvent(
    var events: String? = null,
    var title: String? = null,
    var date_start: Timestamp? = null,
    val date_created: Date? = null
)
