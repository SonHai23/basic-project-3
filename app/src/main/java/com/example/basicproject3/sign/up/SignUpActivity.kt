package com.example.basicproject3.sign.up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.example.basicproject3.MainActivity
import com.example.basicproject3.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_form)

        val signInLink = findViewById<View>(R.id.txtSignInLink) as TextView
        val text = "Already have account? Sign In"
        val ssb = SpannableStringBuilder(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }
        val startIndex = text.indexOf("Sign In")
        val endIndex = startIndex + "Sign In".length
        ssb.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        signInLink.text = ssb
        signInLink.movementMethod = LinkMovementMethod.getInstance()
    }
}