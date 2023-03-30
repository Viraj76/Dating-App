package com.example.datingapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datingapp.R
import com.example.datingapp.adapter.ChatFragmentAdapter
import com.example.datingapp.databinding.FragmentChatragmentBinding
import com.example.datingapp.ui.fragments.DatingFragment.Companion.userDetail


class ChatFragment : Fragment() {

    private lateinit var binding:FragmentChatragmentBinding
    private lateinit var chatFragmentAdapter: ChatFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatragmentBinding.inflate(layoutInflater)

        prepareRvForChatFragmentAdapter()
        showingAllUsers()

        return binding.root
    }

    private fun showingAllUsers() {
        chatFragmentAdapter.setUserList(userDetail!!)
    }

    private fun prepareRvForChatFragmentAdapter() {
        chatFragmentAdapter = ChatFragmentAdapter()
        binding.userChatRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = chatFragmentAdapter
        }
    }


}