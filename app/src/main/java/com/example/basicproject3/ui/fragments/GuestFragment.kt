package com.example.basicproject3.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.basicproject3.R
import com.example.basicproject3.auth.LoginActivity
import com.example.basicproject3.databinding.FragmentGuestBinding
import com.google.firebase.auth.FirebaseAuth

class GuestFragment : Fragment() {
    private var _binding: FragmentGuestBinding? = null
    private val binding get() = _binding!!
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val homeViewModel =
            ViewModelProvider(this).get(GuestViewModel::class.java)*/

        _binding = FragmentGuestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textProfile
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            // Nếu người dùng đã đăng nhập, tiếp tục với app
            findNavController().navigate(R.id.navigate_to_user_fragment)
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến màn hình đăng nhập
            binding.btnSignInLink.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event to back to home fragment
            findNavController().navigate(R.id.action_navigation_guest_to_navigation_home)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}