package com.example.datingapp.adapter

import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.datingapp.databinding.ItemKolodaBinding
import com.example.datingapp.model.UserModel

class DatingAdapter:RecyclerView.Adapter<DatingAdapter.UserViewHolder>() {

    private  var userDetail = ArrayList<UserModel>()
    fun setUserDetail(userDetail: ArrayList<UserModel>){
        this.userDetail = userDetail
        notifyDataSetChanged()
    }

    class UserViewHolder(val binding:ItemKolodaBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemKolodaBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       val details = userDetail[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(details.image).into(userImage)
            userName.text = details.name
            userEmail.text = details.email
        }
    }

    override fun getItemCount(): Int {
        return userDetail.size
    }
}