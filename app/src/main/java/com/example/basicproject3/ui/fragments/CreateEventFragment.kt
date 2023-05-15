package com.example.basicproject3.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.basicproject3.MyEventActivity
import com.example.basicproject3.R
import com.example.basicproject3.auth.LoginActivity
import com.example.basicproject3.databinding.FragmentCreateEventBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateEventFragment : Fragment() {
    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)

        handleCreateClick()

        return binding.root
    }

    private fun handleCreateClick() {
        val db: FirebaseFirestore = Firebase.firestore
        binding.btnCreate.setOnClickListener {
            val eventName = binding.etEventName.text.toString().trim()
            val location = binding.etLocation.text.toString().trim()
            val eventStartTime = binding.etStartTime.text.toString().trim()
            val eventStartDate = binding.etStartDate.text.toString().trim()
            val eventEndTime = binding.etEndTime.text.toString().trim()
            val eventEndDate = binding.etEndDate.text.toString().trim()
            val eventStart = "$eventStartTime, $eventStartDate"
            val eventEnd = "$eventEndTime, $eventEndDate"

            if (eventName.isNotEmpty() &&
                location.isNotEmpty() &&
                eventStartTime.isNotEmpty() &&
                eventStartDate.isNotEmpty() &&
                eventEndTime.isNotEmpty() &&
                eventEndDate.isNotEmpty()) {

                val uid = auth.currentUser?.uid

                val infoEvent = hashMapOf(
                    "eventName" to eventName,
                    "location" to location,
                    "eventStart" to eventStart,
                    "eventEnd" to eventEnd
                )

                db.collection("categories").document("concert").collection(uid!!).document()
                    .set(infoEvent)
                    .addOnSuccessListener {
                        Log.d("TAG", "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error writing document", e)
                    }

                val intent = Intent(activity, MyEventActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(activity, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}