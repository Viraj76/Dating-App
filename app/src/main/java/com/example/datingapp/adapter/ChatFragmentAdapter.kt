package com.example.datingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.datingapp.databinding.ChatItemViewBinding
import com.example.datingapp.model.UserModel

class ChatFragmentAdapter:RecyclerView.Adapter<ChatFragmentAdapter.ChatViewHolder>() {

   private var userList = ArrayList<UserModel>()

    fun setUserList(userList:ArrayList<UserModel>){
        this.userList = userList
        notifyDataSetChanged()
    }

    class ChatViewHolder(val binding:ChatItemViewBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(ChatItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
       val userData  = userList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(userData.image).into(userImage)
            userName.text = userData.name
        }
    }

    override fun getItemCount(): Int {
       return userList.size
    }


}