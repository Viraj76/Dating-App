package com.example.datingapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datingapp.databinding.ActivityMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class MessageActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.send.setOnClickListener{
            val message = binding.message.text.toString()

            if(message.isEmpty()) Toast.makeText(this,"Enter Message",Toast.LENGTH_SHORT).show()
            else
                sendMessage(message)
        }


    }

    private fun sendMessage(message: String) {
        binding.message.text = null
        val receiverId = intent.getStringExtra("userId")
        val senderId  = FirebaseAuth.getInstance().currentUser?.phoneNumber

        val chatId = senderId + receiverId
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())

        val map = hashMapOf<String,String>()
        map["message"] = message
        map["senderId"] = senderId!!
        map["currentDate"] = currentDate
        map["currentTime"] = currentTime

        val reference =  FirebaseDatabase.getInstance().getReference("Chats").child(chatId)
             reference.push().setValue(map)
            .addOnCompleteListener { messageSent->
                if(messageSent.isSuccessful) {

                    Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show()
                }
                else  Toast.makeText(this,messageSent.exception.toString(),Toast.LENGTH_SHORT).show()
            }

    }
}