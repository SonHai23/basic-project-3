package com.example.basicproject3.data

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class Utils {
    companion object {
        fun formatDate(date: Timestamp?): String? {
            val formatter = SimpleDateFormat("MMMM dd, yyyy 'at' HH:mm z", Locale.getDefault())
            return formatter.format(date!!.toDate())
        }

        fun currentTimestamp(): Timestamp {
            val now = Timestamp.now().toDate()
            return Timestamp(now)
        }
    }
}