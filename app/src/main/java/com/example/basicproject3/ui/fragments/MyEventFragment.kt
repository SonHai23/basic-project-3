package com.example.basicproject3.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.CreateEventActivity
import com.example.basicproject3.MyEventActivity
import com.example.basicproject3.R
import com.example.basicproject3.data.model.HappeningEvent
import com.example.basicproject3.data.model.MyEvent
import com.example.basicproject3.databinding.FragmentGuestBinding
import com.example.basicproject3.databinding.FragmentMyEventBinding
import com.example.basicproject3.ui.adapters.HappeningEventAdapter
import com.example.basicproject3.ui.adapters.MyEventAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class MyEventFragment : Fragment() {
    private var _binding: FragmentMyEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var myEventList: ArrayList<MyEvent>
    private var data = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val uid = auth.currentUser?.uid

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyEventBinding.inflate(inflater, container, false)

        recyclerView = binding.rvMyEvents
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        myEventList = arrayListOf()
        getMyEventEvent()

        binding.btnCreateEvent.setOnClickListener() {
            val intent = Intent(activity, CreateEventActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun getMyEventEvent() {
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

        data.collection("events")
            .whereEqualTo("host", uid.toString())
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val myEvent: MyEvent? = data.toObject(MyEvent::class.java)
                        if (myEvent != null) {
                            val imagePath = "events/${data.id}"
//                            Toast.makeText(activity, "${data.id}", Toast.LENGTH_SHORT).show()
                            val imageRef = storage.getReference(imagePath)
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                myEvent.events = uri.toString()
                                myEventList.add(myEvent)
                                recyclerView.adapter = MyEventAdapter(myEventList)

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