package com.example.datingapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datingapp.R
import com.example.datingapp.adapter.DatingAdapter
import com.example.datingapp.databinding.FragmentDatingBinding
import com.example.datingapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DatingFragment : Fragment() {

    private lateinit var binding:FragmentDatingBinding
    private lateinit var datingAdapter: DatingAdapter
    private lateinit var userDetail : ArrayList<UserModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDatingBinding.inflate(layoutInflater)

        userDetail = ArrayList()
        prepareRecyclerViewForDatingAdapter()
        getUserData()
        return binding.root
    }

    private fun prepareRecyclerViewForDatingAdapter() {
        datingAdapter = DatingAdapter()
        binding.usersRv.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = datingAdapter
        }
    }

    private fun getUserData() {
        FirebaseDatabase.getInstance().getReference("Users")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(userDetails in snapshot.children){
                        val currentUser = FirebaseAuth.getInstance().currentUser?.phoneNumber
                        val userData = userDetails.getValue(UserModel::class.java)
                        if(currentUser!=userData?.number)
                            userDetail.add(userData!!)

                    }

                    datingAdapter.setUserDetail(userDetail)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

}