package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Profile user"
    }
    val text: LiveData<String> = _text
}