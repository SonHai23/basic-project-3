package com.example.basicproject3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basicproject3.databinding.ActivityEventBinding
import com.example.basicproject3.ui.fragments.EventFragment

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val event = EventFragment()
        event.arguments = intent.extras
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.manage_event_fragment, event)
            commit()
        }
    }
}