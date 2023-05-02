package com.example.basicproject3.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.basicproject3.R
import com.example.basicproject3.databinding.FragmentChangeProfileBinding
import com.example.basicproject3.databinding.FragmentUserBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ChangeProfileFragment : Fragment() {
    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!
    private var imageReference = Firebase.storage.reference
    private var currentFile: Uri? = null

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_change_profile, container, false)

        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imgAvatar.setOnClickListener() {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                imageLauncher.launch(it)
            }
        }

        binding.btnSaveChanges.setOnClickListener {
//            uploadImageToStorage("avatar")
            uploadImageToStorage(Firebase.auth.currentUser?.uid.toString())
            findNavController().navigate(R.id.navigate_change_profile_fragment_to_user_fragment)
        }

        return root
    }

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let {
                currentFile = it
                binding.imgAvatar.setImageURI(it)
            }
        } else {
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageToStorage(filename: String) {
        try {
            currentFile?.let {
                imageReference.child("profiles/$filename").putFile(it)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error on upload", Toast.LENGTH_SHORT).show()
                    }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}