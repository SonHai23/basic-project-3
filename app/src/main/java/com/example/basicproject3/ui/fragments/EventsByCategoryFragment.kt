package com.example.basicproject3.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.basicproject3.databinding.FragmentEventByCategoryBinding
import com.example.basicproject3.ui.adapters.EventListAdapter
import com.example.basicproject3.ui.viewmodels.EventsByCategoryViewModel
import kotlinx.coroutines.launch

class EventsByCategoryFragment : Fragment() {

    private var _binding: FragmentEventByCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val eventsByCategoryViewModel =
            ViewModelProvider(this)[EventsByCategoryViewModel::class.java]

        _binding = FragmentEventByCategoryBinding.inflate(inflater, container, false)

        //load Event list
        lifecycleScope.launch {
            binding.progressBarEvent.visibility = View.VISIBLE
            val categoryName = arguments?.getString("categoryName").toString()
            val eventList = eventsByCategoryViewModel.getEventList(categoryName)
            val recyclerView = binding.recyclerViewEvents
            recyclerView.adapter = EventListAdapter(eventList)
            binding.progressBarEvent.visibility = View.GONE
        }

        return binding.root
    }
}