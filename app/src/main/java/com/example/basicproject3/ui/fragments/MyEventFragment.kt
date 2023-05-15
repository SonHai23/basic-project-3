package com.example.basicproject3.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basicproject3.CreateEventActivity
import com.example.basicproject3.MyEventActivity
import com.example.basicproject3.R
import com.example.basicproject3.databinding.FragmentGuestBinding
import com.example.basicproject3.databinding.FragmentMyEventBinding

class MyEventFragment : Fragment() {
    private var _binding: FragmentMyEventBinding? = null
    private val binding get() = _binding!!

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyEventBinding.inflate(inflater, container, false)

        binding.btnCreateEvent.setOnClickListener() {
            val intent = Intent(activity, CreateEventActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}