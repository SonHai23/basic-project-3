package com.example.basicproject3.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicproject3.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Source

class UserViewModel : ViewModel() {

    private var auth: FirebaseAuth
    private val user = User()

    data class UserInformation(
        val name: String,
        val email: String
    )

    private val _profileInformation = MutableLiveData<UserInformation>().apply {
        /*val data = Firebase.firestore
        val uid = auth.currentUser?.uid
        val docRef = data.collection("users").document(uid!!)*/

        auth = FirebaseAuth.getInstance()

        user.getUsername().addOnSuccessListener {
            if (it != null) {
//                    val userProfile = document.getString("name")
//                    val userProfile = it.data?.("name").toString()
                val userProfile = it.data?.get("name").toString()
                val userEmailProfile = auth.currentUser?.email.toString()

                val profile = UserInformation(userProfile, userEmailProfile)
                value = profile
            }
        }
    }

    private val _profileAvatar = MutableLiveData<String>().apply {
        user.getAvatar().addOnSuccessListener {
            value = it.toString()
//            value = Picasso.get().load(imageUrl).placeholder(R.drawable.avatar_profile_default).into(imageView)
        }
    }
    val profileInformation: LiveData<UserInformation> = _profileInformation
    val profileAvatar: LiveData<String> = _profileAvatar
}