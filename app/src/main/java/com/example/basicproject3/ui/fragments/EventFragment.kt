package com.example.basicproject3.ui.fragments

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.basicproject3.data.Utils.Companion.formatDate
import com.example.basicproject3.R
import com.example.basicproject3.TicketDetailsActivity
import com.example.basicproject3.data.model.Event
import com.example.basicproject3.data.model.Ticket
import com.example.basicproject3.databinding.FragmentEventBinding
import com.example.basicproject3.ui.viewmodels.EventViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private var isFavorite = false

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
        binding.txtCurrentEventLocation.setOnClickListener {
            searchLocationOnGoogleMaps(event.location.toString())
        }
        binding.txtDateStart.append(formatDate(event.date_start))
        binding.txtDateEnd.append(formatDate(event.date_end))

        lifecycleScope.launch {
            val hasTicket = event.hasTicket(auth.currentUser!!.uid)
            val ticket: Ticket = event.buyTicket(auth.currentUser!!.uid)
            val hadTicketText = "You already have a ticket for this event!"
            if (!hasTicket) {
                binding.btnGetTicket.setOnClickListener {
                    it.isClickable = false

                    binding.btnGetTicket.text = hadTicketText
                    val intent = Intent(context, TicketDetailsActivity::class.java)
                    val bundle = Bundle().apply {
                        putParcelable("ticket", ticket)
                    }
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
            } else {
                binding.btnGetTicket.isClickable = false
                binding.btnGetTicket.text = hadTicketText
            }
            val organizer = event.getOrganizer()
            if (organizer != null) {
                binding.organizer.txtOrganizerName.text = organizer.name
                organizer.getProfileAvatar().addOnSuccessListener {
                    Glide.with(requireActivity()).load(it)
                        .into(binding.organizer.imgOrganizerAvatar)
                }
            }
        }

        /*isFavorite = !isFavorite
        val heartIcon = view as ImageView
        if (isFavorite) {
            heartIcon.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.black))
        } else {
            heartIcon.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.white))
        }*/

        val heartIcon = binding.imdFavourite
        heartIcon.setOnClickListener {
            toggleFavorite(it)
        }

        return binding.root
    }

    private fun toggleFavorite(view: View) {
        isFavorite = !isFavorite
        val heartIcon = view as ImageView
        if (isFavorite) {
            heartIcon.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.black))
        } else {
            heartIcon.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.red))
        }
    }

    private fun searchLocationOnGoogleMaps(location: String) {
        val gmmIntentUri = Uri.parse("geo:12.3456, 67.8901?q=$location")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}