package com.example.basicproject3.data.model

import java.util.Date

class Event(
    id: String,
    name: String,
    description: String,
    address: String,
    city: String,
    phone: String,
    images: List<Image>,
    category: String,
    age_of_restrict: Int,
    ticket_price: Int,
    ticket_quantity: Int,
    date_start: Date,
    date_end: Date,
) {
}