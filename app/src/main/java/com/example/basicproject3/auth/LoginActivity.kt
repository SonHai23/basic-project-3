package com.example.basicproject3.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basicproject3.R
import com.example.basicproject3.databinding.ActivityLoginBinding
import com.example.basicproject3.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}