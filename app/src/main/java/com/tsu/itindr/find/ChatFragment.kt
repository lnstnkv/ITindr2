package com.tsu.itindr.find

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tsu.itindr.request.chat.GetChatController
import com.tsu.itindr.R
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.databinding.FragmentChatBinding

class ChatFragment:Fragment(R.layout.fragment_chat) {
    companion object{
        val TAG = ChatFragment::class.java.simpleName
        fun newInstance()= ChatFragment()
    }
    private lateinit var binding: FragmentChatBinding
    private val chatAdapterListener=object : ChatAdapter.ChatAdapterListener{
        override fun onItemClick(item: ProfileItem) {
            print(item)
        }
    }
    private val chatAdapter=ChatAdapter(chatAdapterListener)

    private fun initView()= with(binding){
        chatRecycler.apply {
            adapter=chatAdapter
            addItemDecoration(ChatItemDecoration())
        }
    }

    private var controller= GetChatController()
    val profile: MutableList<Profile> = mutableListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = SharedPreference(activity as FindActivity)
        binding = FragmentChatBinding.bind(view)
        initView()
        controller.getChat(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {
                //Toast.makeText(activity, R.string.people, Toast.LENGTH_LONG).show()
                        val profileItems= mutableListOf<ProfileItem>()
                for (getChat in it) {
                    profileItems.add(
                        ProfileItem(
                        id = getChat.chat.id,
                        username = getChat.chat.title,
                        lastMessage = getChat.lastMessage?.text,
                        avatar = getChat.chat.avatar
                        )
                    )
                }
                chatAdapter.submitList(profileItems)
            },
            onFailure = {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            }

        )
    }
}
