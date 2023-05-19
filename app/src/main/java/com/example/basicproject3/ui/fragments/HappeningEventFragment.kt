package com.example.basicproject3.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.data.model.HappeningEvent
import com.example.basicproject3.databinding.FragmentRecentlyEventBinding
import com.example.basicproject3.ui.adapters.HappeningEventAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class HappeningEventFragment : Fragment() {
    private var _binding: FragmentRecentlyEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<HappeningEvent>
//    private lateinit var eventList: MutableList<Event>
    private var data = Firebase.firestore

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyEventBinding.inflate(inflater, container, false)

        newRecyclerView = binding.rvHappeningEvent
        newRecyclerView.layoutManager = LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()
        getHappeningEvent()

//        eventList = mutableListOf()
//        getHappeningEvents()
//        binding.rvHappeningEvent.adapter = EventListAdapter(requireContext(), eventList)

        return binding.root
    }

//    private fun getHappeningEvents() {
//        data.collection("events").orderBy("date_created", Query.Direction.DESCENDING).limit(100)
//            .get()
//            .addOnSuccessListener {
//                val list = mutableListOf<Event>()
//                for (document in it) {
//                    if (it != null) {
//                        val event = document.toObject<Event>()
//                        list.add(event)
//                    }
//                }
//                eventList.addAll(list)
//            }
//    }

    private fun getHappeningEvent() {/*for (i in imageID.indices) {
            val happeningEvents = HappeningEvent(imageID[i], heading[i])
            newArrayList.add(happeningEvents)
        }
        newRecyclerView.adapter = HappeningEventAdapter(newArrayList)*/

        data = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()

        /*data.collection("events").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val happening: HappeningEvent? = data.toObject(HappeningEvent::class.java)
                        if (happening != null) {
                            newArrayList.add(happening)
//                            Toast.makeText(activity, "$it", Toast.LENGTH_SHORT).show()
                        }
                    }
                    newRecyclerView.adapter = HappeningEventAdapter(newArrayList) // Danh cho viec xu ly khi chua xu ly image tuwf storage
                }
            }
            .addOnFailureListener() {
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }*/

        data.collection("events").orderBy("date_created", Query.Direction.DESCENDING).limit(100)
            .get().addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val happening: HappeningEvent? = data.toObject(HappeningEvent::class.java)
                        if (happening != null) {
                            val imagePath = "events/${data.id}"
//                            Toast.makeText(activity, "${data.id}", Toast.LENGTH_SHORT).show()
                            val imageRef = storage.getReference(imagePath)
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                happening.events = uri.toString()
                                newArrayList.add(happening)
                                newArrayList.sortByDescending { it.date_created }
                                newRecyclerView.adapter = HappeningEventAdapter(newArrayList)

//                                Toast.makeText(activity, "$it", Toast.LENGTH_SHORT).show()
                            }
//                            organizerList.add(organizer)
//                            Toast.makeText(activity, "$organizer", Toast.LENGTH_SHORT).show()
                        }
                    }
//                    recyclerView.adapter = OrganizersToFollowAdapter(organizerList) // Danh cho viec xu ly khi chua xu ly image tuwf storage
                }
            }.addOnFailureListener() {
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}