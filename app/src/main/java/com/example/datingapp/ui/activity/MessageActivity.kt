package com.example.datingapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datingapp.adapter.MessageAdapter
import com.example.datingapp.databinding.ActivityMessageBinding
import com.example.datingapp.model.Messages
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_message.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMessageBinding
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Message"

        prepareRvForMessageAdapter()

        verifyChatId()
//        getMessages(intent.getStringExtra("chatId"))

        binding.send.setOnClickListener{
            val message = binding.message.text.toString()
            binding.message.text.clear()
            if(message.isEmpty()) Toast.makeText(this,"Enter Message",Toast.LENGTH_SHORT).show()
            else
                storeMessage(message)
        }


    }

    private var senderId:String?= null
    private var chatId:String?= null

    private fun verifyChatId() {
        val receiverId = intent.getStringExtra("userId")
         senderId  = FirebaseAuth.getInstance().currentUser?.phoneNumber
         chatId = senderId + receiverId
        val reverseChatId =  receiverId + senderId
        val reference=FirebaseDatabase.getInstance().getReference("Chats")
        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChild(chatId!!)){
                    getMessages(chatId)
                }
                else if(snapshot.hasChild(reverseChatId)){
                    chatId = reverseChatId
                    getMessages(chatId)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MessageActivity,error.message,Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun prepareRvForMessageAdapter() {
        messageAdapter = MessageAdapter(binding.rvChats,this)
        binding.rvChats.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter  = messageAdapter
        }
    }



    private fun getMessages(chatId: String?) {
        FirebaseDatabase.getInstance().getReference("Chats").child(chatId!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messagesList = ArrayList<Messages>()
                    for(messages in snapshot.children){
                        val message = messages.getValue(Messages::class.java)
                        messagesList.add(message!!)
                    }
                    messageAdapter.setMessageList(messagesList)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MessageActivity, error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun storeMessage(message: String) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())
        val map = hashMapOf<String,String>()
        map["message"] = message
        map["senderId"] = senderId!!
        map["currentDate"] = currentDate
        map["currentTime"] = currentTime
        val reference=FirebaseDatabase.getInstance().getReference("Chats").child(chatId!!)
        reference.push().setValue(map)
            .addOnCompleteListener { messageSent->
                if(messageSent.isSuccessful) {
                    Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show()
                }
            }

    }
}