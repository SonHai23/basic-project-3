package com.example.basicproject3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basicproject3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ẩn thanh tiêu đề ứng dụng
//        requestWindowFeature(Window.FEATURE_NO_TITLE)

//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
//        Handler().postDelayed({
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 1000)

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
        })

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