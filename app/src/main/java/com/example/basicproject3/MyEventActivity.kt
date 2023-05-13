package com.example.basicproject3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basicproject3.databinding.ActivityMyEventBinding
import com.example.basicproject3.ui.fragments.MyEventFragment

class MyEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myEvent = MyEventFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.manage_actions, myEvent)
            commit()
        }
    }
}