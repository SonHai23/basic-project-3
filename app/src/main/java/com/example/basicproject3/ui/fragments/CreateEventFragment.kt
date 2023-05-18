package com.example.basicproject3.ui.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.Calendar
import java.util.Date
import java.util.Locale

//class CreateEventFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
class CreateEventFragment : Fragment() {
    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private var imageReference = Firebase.storage.reference
    private var currentFile: Uri? = null

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)

//        pickDate()

        handleCreateClick()

        return binding.root
    }

    private fun handleCreateClick() {
        val db: FirebaseFirestore = Firebase.firestore
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        binding.imgEventDescription.setOnClickListener() {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                imageLauncher.launch(it)
            }
        }

        /*binding.etStartDate.setOnClickListener {
            // create DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    // set calendar with selected date
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    // display selected date on EditText
                    binding.etStartDate.setText(SimpleDateFormat("dd/MM/yyyy").format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // show DatePickerDialog
            datePickerDialog.show()
        }*/

        // Create DatePickerDialog and show it on EditText click
        binding.etStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    // Save selected date to calendar instance
                    calendarStart.set(year, month, dayOfMonth)

                    // Create TimePickerDialog to let user choose time
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            // Save selected hour and minute to calendar instance
                            calendarStart.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendarStart.set(Calendar.MINUTE, minute)

                            // Format selected date and time with desired pattern
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            val formattedDateTime = dateFormat.format(calendarStart.time)

                            // Set formatted date and time to EditText
                            binding.etStartDate.setText(formattedDateTime)
                        },
                        calendarStart.get(Calendar.HOUR_OF_DAY),
                        calendarStart.get(Calendar.MINUTE),
                        false
                    )
                    timePickerDialog.show()
                },
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.etEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    // Save selected date to calendar instance
                    calendarEnd.set(year, month, dayOfMonth)

                    // Create TimePickerDialog to let user choose time
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            // Save selected hour and minute to calendar instance
                            calendarEnd.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendarEnd.set(Calendar.MINUTE, minute)

                            // Format selected date and time with desired pattern
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            val formattedDateTime = dateFormat.format(calendarEnd.time)

                            // Set formatted date and time to EditText
                            binding.etEndDate.setText(formattedDateTime)
                        },
                        calendarEnd.get(Calendar.HOUR_OF_DAY),
                        calendarEnd.get(Calendar.MINUTE),
                        false
                    )
                    timePickerDialog.show()
                },
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }


        binding.btnCreate.setOnClickListener {
            val eventName = binding.etEventName.text.toString().trim()
            val location = binding.etLocation.text.toString().trim()
            val categories = binding.etCategories.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val currentDate = Date()
            /*val eventStartDate = binding.etStartDate.text.toString()
            val eventEndDate = binding.etEndDate.text.toString()*/

            if (eventName.isNotEmpty() &&
                location.isNotEmpty() &&
                categories.isNotEmpty() &&
                description.isNotEmpty() /*&&
                eventStartDate.isNotEmpty() &&
                eventEndDate.isNotEmpty()*/) {

                val uid = auth.currentUser?.uid
                val timestampStart = Timestamp(calendarStart.time)
                val timestampEnd = Timestamp(calendarEnd.time)

                val infoEvent = hashMapOf(
                    "title" to eventName,
                    "location" to location,
                    "description" to description,
                    "date_start" to timestampStart,
                    "date_end" to timestampEnd,
                    "category" to categories,
                    "host" to uid,
                    "date_created" to currentDate,
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