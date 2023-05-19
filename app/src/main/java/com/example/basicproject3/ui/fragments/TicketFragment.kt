package com.example.basicproject3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.data.model.HappeningEvent
import com.example.basicproject3.databinding.FragmentTicketBinding
import com.example.basicproject3.ui.adapters.HappeningEventAdapter
import com.example.basicproject3.ui.viewmodels.TicketViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class TicketFragment : Fragment() {

    private var _binding: FragmentTicketBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketList: ArrayList<HappeningEvent>
    private var data = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val uid = auth.currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ticketViewModel =
            ViewModelProvider(this)[TicketViewModel::class.java]

        _binding = FragmentTicketBinding.inflate(inflater, container, false)

        /*val textView: TextView = binding.textNotifications
        ticketViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        recyclerView = binding.rvTickets
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        ticketList = arrayListOf()
        getTicket()

        return binding.root
    }

    private fun getTicket() {
        /*for (i in imageID.indices) {
            val happeningEvents = HappeningEvent(imageID[i], heading[i])
            newArrayList.add(happeningEvents)
        }
        newRecyclerView.adapter = HappeningEventAdapter(newArrayList)*/

        data = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()

        data.collection("events")// tính sửa ở đây để truy vấn theo uid của collection phụ
            .whereEqualTo("uid", uid.toString())// đây là điều kiện
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val happening: HappeningEvent? = data.toObject(HappeningEvent::class.java)
                        if (happening != null) {
                            val imagePath = "events/${data.id}"
//                            Toast.makeText(activity, "${data.id}", Toast.LENGTH_SHORT).show()
                            val imageRef = storage.getReference(imagePath)
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                happening.events = uri.toString()
                                ticketList.add(happening)
                                ticketList.sortByDescending { it.date_created }
                                recyclerView.adapter = HappeningEventAdapter(ticketList)

//                                Toast.makeText(activity, "$it", Toast.LENGTH_SHORT).show()
                            }
//                            organizerList.add(organizer)
//                            Toast.makeText(activity, "$organizer", Toast.LENGTH_SHORT).show()
                        }
                    }
//                    recyclerView.adapter = OrganizersToFollowAdapter(organizerList) // Danh cho viec xu ly khi chua xu ly image tuwf storage
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