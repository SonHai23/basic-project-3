package com.example.basicproject3.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.basicproject3.data.model.Event
import com.example.basicproject3.databinding.FragmentRecentlyEventBinding
import com.example.basicproject3.ui.adapters.EventListAdapter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HappeningEventFragment : Fragment() {
    private var _binding: FragmentRecentlyEventBinding? = null
    private val binding get() = _binding!!

    private var data = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyEventBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            binding.rvHappeningEvent.adapter =
                EventListAdapter(requireContext(), getHappeningEvents())
        }

        return binding.root
    }

    private suspend fun getHappeningEvents(): List<Event> {
        val collection =
            data.collection("events").orderBy("date_created", Query.Direction.DESCENDING).limit(100)
                .get().await()
        val list = mutableListOf<Event>()
        for (document in collection) {
            if (document != null) {
                val event = document.toObject<Event>()
                list.add(event)
            }
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}