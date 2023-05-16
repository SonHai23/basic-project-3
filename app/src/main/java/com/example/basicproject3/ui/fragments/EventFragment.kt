package com.example.basicproject3.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.basicproject3.databinding.FragmentEventBinding
import com.example.basicproject3.ui.viewmodels.EventViewModel
import kotlinx.coroutines.launch

class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]

        _binding = FragmentEventBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            binding.progressBarEventPage.visibility = View.VISIBLE
            val category = arguments?.getString("category").toString()
            val eventId = arguments?.getString("eventId").toString()
            val event = eventViewModel.getEvent(category, eventId)
            binding.txtCurrentEventTitle.text = event.title
            binding.txtCurrentEventDescription.text = event.description
            binding.txtCurrentEventLocation.text = event.location
            binding.progressBarEventPage.visibility = View.GONE
        }


        return binding.root
    }

}