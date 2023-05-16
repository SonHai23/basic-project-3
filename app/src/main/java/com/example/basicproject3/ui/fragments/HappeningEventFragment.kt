package com.example.basicproject3.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.R
import com.example.basicproject3.data.model.HappeningEvent
import com.example.basicproject3.data.model.OrganizersToFollow
import com.example.basicproject3.data.model.PopularEvents
import com.example.basicproject3.databinding.FragmentRecentlyEventBinding
import com.example.basicproject3.ui.adapters.HappeningEventAdapter
import com.example.basicproject3.ui.adapters.OrganizersToFollowAdapter
import com.example.basicproject3.ui.adapters.PopularEventsAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class HappeningEventFragment : Fragment() {
    private var _binding: FragmentRecentlyEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<HappeningEvent>
    private var data = Firebase.firestore
    /*lateinit var imageID: Array<Int>
    lateinit var heading: Array<String>*/

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentlyEventBinding.inflate(inflater, container, false)

        /*imageID = arrayOf(
            R.drawable.edm1,
            R.drawable.edm2,
            R.drawable.edm3,
            R.drawable.edm4,
            R.drawable.edm5,
            R.drawable.edm6,
            R.drawable.edm7,
            R.drawable.edm8,
            R.drawable.edm9,
        )

        heading = arrayOf(
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
        )*/

        newRecyclerView = binding.rvHappeningEvent
        newRecyclerView.layoutManager = LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()
        getHappeningEvent()

        return binding.root
    }

    private fun getHappeningEvent() {
        /*for (i in imageID.indices) {
            val happeningEvents = HappeningEvent(imageID[i], heading[i])
            newArrayList.add(happeningEvents)
        }
        newRecyclerView.adapter = HappeningEventAdapter(newArrayList)*/

        data = FirebaseFirestore.getInstance()
//        val storage = FirebaseStorage.getInstance()

        data.collection("categories").document("concert").collection("events").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val happening: HappeningEvent? = data.toObject(HappeningEvent::class.java)
                        if (happening != null) {
                            newArrayList.add(happening)
//                            Toast.makeText(activity, "$organizer", Toast.LENGTH_SHORT).show()
                        }
                    }
                    newRecyclerView.adapter = HappeningEventAdapter(newArrayList) // Danh cho viec xu ly khi chua xu ly image tuwf storage
                }
            }
            .addOnFailureListener() {
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}