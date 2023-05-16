package com.example.basicproject3.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.adapters.DatePickerBindingAdapter
import com.example.basicproject3.MyEventActivity
import com.example.basicproject3.R
import com.example.basicproject3.auth.LoginActivity
import com.example.basicproject3.databinding.FragmentCreateEventBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class CreateEventFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

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
                    "host" to uid,
                )

                db.collection("categories").document(categories).collection("events").document()
                    .set(infoEvent)
                    .addOnSuccessListener {
                        Log.d("TAG", "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error writing document", e)
                    }

                val intent = Intent(activity, MyEventActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(activity, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}