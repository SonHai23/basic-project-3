package com.example.basicproject3.ui.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.adapters.DatePickerBindingAdapter
import androidx.navigation.fragment.findNavController
import com.example.basicproject3.MyEventActivity
import com.example.basicproject3.R
import com.example.basicproject3.auth.LoginActivity
import com.example.basicproject3.databinding.FragmentCreateEventBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.Calendar

class CreateEventFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private var imageReference = Firebase.storage.reference
    private var currentFile: Uri? = null

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)

        pickDate()

        handleCreateClick()

        return binding.root
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        binding.etStartDate.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.etEndDate.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        binding.etStartDate.setText("$savedDay/$savedMonth/$savedYear\n$savedHour:$savedMinute")
        binding.etEndDate.setText("$savedDay/$savedMonth/$savedYear\n$savedHour:$savedMinute")
    }

    private fun handleCreateClick() {
        val db: FirebaseFirestore = Firebase.firestore

        binding.imgEventDescription.setOnClickListener() {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                imageLauncher.launch(it)
            }
        }

        binding.btnCreate.setOnClickListener {
            val eventName = binding.etEventName.text.toString().trim()
            val location = binding.etLocation.text.toString().trim()
            val categories = binding.etCategories.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val eventStartDate = binding.etStartDate.text.toString().trim()
            val eventEndDate = binding.etEndDate.text.toString().trim()

            if (eventName.isNotEmpty() &&
                location.isNotEmpty() &&
                categories.isNotEmpty() &&
                description.isNotEmpty() &&
                eventStartDate.isNotEmpty() &&
                eventEndDate.isNotEmpty()) {

                val uid = auth.currentUser?.uid

                val infoEvent = hashMapOf(
                    "title" to eventName,
                    "location" to location,
                    "description" to description,
                    "date_start" to eventStartDate,
                    "date_end" to eventEndDate,
                    "category" to categories,
                    "host" to uid,
                )

                // Xu ly dua du lieu vao firestore
                db.collection("events")
                    .add(infoEvent)
                    .addOnSuccessListener {
//                        val imageId = java.util.UUID.randomUUID().toString()
                        val imageId = "${it.id}"
                        uploadImageToStorage(imageId) // Luu anh vao storage voi ten la id document ngau nhien
                        Log.d("TAG", "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error writing document", e)
                    }

                /*val imageId = java.util.UUID.randomUUID().toString()
                uploadImageToStorage(imageId)*/

//                Toast.makeText(activity, "$test", Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, MyEventActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(activity, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                currentFile = it
                binding.imgEventDescription.setImageURI(it)
            }
        } else {
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageToStorage(filename: String) {
        try {
            currentFile?.let {
                imageReference.child("events/$filename").putFile(it)
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