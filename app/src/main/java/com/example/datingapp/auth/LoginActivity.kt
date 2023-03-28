package com.example.datingapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.datingapp.MainActivity
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var firebaseAuth = FirebaseAuth.getInstance()
    private var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressBarNumber.visibility = GONE
        binding.apply {

            btnSendOtp.setOnClickListener {
                if (binding.userNumber.text!!.isEmpty())
                    binding.userNumber.error = "Please Enter Your Number"
                else {
                    sendOTP(binding.userNumber.text.toString())
                }
            }

            btnVerifyOtp.setOnClickListener {
                if (binding.userOtp.text!!.isEmpty())
                    binding.userOtp.error = "Please Enter Your OTP"
                else {
                    verifyOTP(binding.userOtp.text.toString())
                }
            }

        }
    }

    private fun verifyOTP(userOtp: String) {
        progressBarNumber.visibility = VISIBLE
        val credential = PhoneAuthProvider.getCredential(verificationId!!, userOtp)
        signInWithPhoneAuthCredential(credential)
    }


    private fun sendOTP(userNumber: String) {
        progressBarNumber.visibility = VISIBLE
        
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                progressBarNumber.visibility = VISIBLE
                signInWithPhoneAuthCredential(credential)
            }
            
            override fun onVerificationFailed(e: FirebaseException) {
            }
            
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@LoginActivity.verificationId = verificationId
                progressBarNumber.visibility = GONE
                binding.cvNumberLayout.visibility = GONE
                binding.cvOtpLayout.visibility = VISIBLE
                progressBarNumber.visibility = GONE
            }
        }
        
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+91$userNumber")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
    
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                progressBarNumber.visibility = VISIBLE
                if (task.isSuccessful) {
                    progressBarNumber.visibility = VISIBLE
                    checkUser(binding.userNumber.text.toString())

                } else {
                    Toast.makeText(this,task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkUser(userNumber: String) {

    }


}