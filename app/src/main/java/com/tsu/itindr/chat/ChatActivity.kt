package com.tsu.itindr.chat

import android.content.SharedPreferences
import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tsu.itindr.R
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.chat.MessageController
import com.tsu.itindr.databinding.ActivityChatBinding
import com.tsu.itindr.databinding.FragmentPeopleBinding
import com.tsu.itindr.find.people.PeopleAdapter
import com.tsu.itindr.find.people.PeopleItemDecoration
import com.tsu.itindr.find.people.PeopleViewModel
import com.tsu.itindr.find.people.model.PeopleProfile
import android.content.Intent
import android.util.Log
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.chat.model.Message
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.databinding.ActivityAuthorizationBinding
import com.tsu.itindr.edit.EditViewModel
import com.tsu.itindr.room.chat.ChatRepository
import kotlinx.coroutines.launch


class ChatActivity : AppCompatActivity() {


    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater) }

    private val viewModel by lazy { ViewModelProvider(this).get(ChatMessageViewModel::class.java) }

    private val messageAdapterListener = object : MessageAdapter.MessageAdapterListener {

        override fun onItemClick(item: Message) {
            println()
        }
    }

    private val messageAdapter = MessageAdapter(messageAdapterListener)

    private fun initView() = with(binding) {

        messageRecycler.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        messageRecycler.apply {
            adapter = messageAdapter

            viewModel.isErrorGetMessage.observe(this@ChatActivity) { isError ->
                if (isError) {
                    Toast.makeText(this@ChatActivity, "Ошибка чата", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.getMessageItem.observe(this@ChatActivity) { chatItem ->
                        if (chatItem != null) {
                            messageAdapter.submitList(chatItem)
                        }
                    }
                }
            }


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()

        val intent = intent

        val fName = intent.getStringExtra("chatID")
        fName?.let {
            viewModel.getMessage(fName)
        }


    }
}