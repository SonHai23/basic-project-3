package com.example.basicproject3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basicproject3.databinding.ActivityCreateEventBinding
import com.example.basicproject3.ui.fragments.CreateEventFragment
import com.example.basicproject3.ui.fragments.MyEventFragment

class CreateEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val createEvent = CreateEventFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.manage_create_event_fragment, createEvent)
            commit()
        }
    }
}