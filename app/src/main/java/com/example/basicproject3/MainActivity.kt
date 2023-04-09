package com.example.basicproject3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.basicproject3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ẩn thanh tiêu đề ứng dụng
        requestWindowFeature(Window.FEATURE_NO_TITLE)

//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        /*val email = findViewById<EditText>(R.id.etSignInEmail)
        val password = findViewById<EditText>(R.id.etSignInPassword)
        val signIn = findViewById<Button>(R.id.btnSignIn)

        signIn.setOnClickListener(View.OnClickListener {
            if (email.text.toString() == "email" && password.text.toString() == "password") {
                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(applicationContext, "Login Failure", Toast.LENGTH_SHORT).show()
            }
        })=

*/
        /*val signUpLink = findViewById<View>(R.id.txtSignUpLink) as TextView
        val text = "Don't have an account? Sign Up"
        val ssb = SpannableStringBuilder(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }
        val startIndex = text.indexOf("Sign Up")
        val endIndex = startIndex + "Sign Up".length
        ssb.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        signUpLink.text = ssb
        signUpLink.movementMethod = LinkMovementMethod.getInstance()*/
    }
}