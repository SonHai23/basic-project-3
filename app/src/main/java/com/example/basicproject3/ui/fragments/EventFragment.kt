package com.example.basicproject3.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.basicproject3.data.model.Event
import com.example.basicproject3.databinding.FragmentEventBinding
import com.example.basicproject3.ui.viewmodels.EventViewModel

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
                Glide.with(this).load(it).into(binding.imgEvent)
            }
            binding.txtCurrentEventTitle.text = event.title
            binding.txtCurrentEventDescription.text = event.description
            binding.txtCurrentEventLocation.text = event.location


        return binding.root
    }

}