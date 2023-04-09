package com.example.basicproject3.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basicproject3.R
import com.example.basicproject3.databinding.ActivityLoginBinding
import com.example.basicproject3.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}