package com.example.basicproject3.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.basicproject3.data.Utils.Companion.formatDate
import com.example.basicproject3.data.model.Event
import com.example.basicproject3.databinding.FragmentEventBinding
import com.example.basicproject3.ui.viewmodels.EventViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

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
        binding.txtDateStart.append(formatDate(event.date_start))
        binding.txtDateEnd.append(formatDate(event.date_end))
        lifecycleScope.launch {
            val hasTicket = event.hasTicket(auth.currentUser!!.uid)
            if (!hasTicket) {
                binding.btnGetTicket.setOnClickListener {
                    event.buyTicket(auth.currentUser!!.uid)
                    it.isClickable = false
                }
            } else {
                binding.btnGetTicket.isClickable = false
            }
        }

        lifecycleScope.launch {
            val organizer = event.getOrganizer()
            if (organizer != null) {
                binding.organizer.txtOrganizerName.text = organizer.name
                organizer.getProfileAvatar().addOnSuccessListener {
                    Glide.with(requireActivity()).load(it)
                        .into(binding.organizer.imgOrganizerAvatar)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}