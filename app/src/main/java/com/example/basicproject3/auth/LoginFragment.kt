package com.example.basicproject3.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.basicproject3.databinding.FragmentLoginBinding
import com.example.basicproject3.home.HomeActivity
import com.example.basicproject3.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sign_in, container, false)

//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
//        binding.lifecycleOwner = viewLifecycleOwner
//        return binding.root

        auth = FirebaseAuth.getInstance()

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // Set click listener for button
        binding.btnSignIn.setOnClickListener {
            val email = binding.etSignInEmail.text.toString()
            val password = binding.etSignInPassword.text.toString()

            // Handle button click event
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(activity, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle when click Sign up
        val text = "Don't have an account? Sign up"
        val ssb = SpannableStringBuilder(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(activity, RegisterActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }
        val startIndex = text.indexOf("Sign up")
        val endIndex = startIndex + "Sign up".length
        ssb.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtSignUpLink.text = ssb
        binding.txtSignUpLink.movementMethod = LinkMovementMethod.getInstance()

        return rootView
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
            }
    }
}