package com.example.basicproject3.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.basicproject3.data.Utils
import com.example.basicproject3.data.model.Event
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

        val event = arguments?.getParcelable<Event>("event")!!
        event.getImgUrl().addOnSuccessListener {
            Glide.with(this).load(it).into(binding.imgCurrentEvent)
        }
        binding.txtCurrentEventTitle.text = event.title
        binding.txtCurrentEventDescription.text = event.description
        binding.txtCurrentEventLocation.text = event.location
        binding.txtDateStart.append(Utils.formatDate(event.date_start))
        binding.txtDateEnd.append(Utils.formatDate(event.date_end))

        lifecycleScope.launch {
            val organizer = eventViewModel.getOrganizer(event.host.toString())
            binding.organizer.txtOrganizerName.text = organizer.name
            organizer.getProfileAvatar().addOnSuccessListener {
                Glide.with(requireActivity()).load(it).into(binding.organizer.imgOrganizerAvatar)
            }
        }

        return binding.root
    }

}