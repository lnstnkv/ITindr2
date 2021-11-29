package com.tsu.itindr.find.chat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.data.chat.GetChatController
import com.tsu.itindr.R
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.databinding.FragmentChatBinding
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.find.Profile
import com.tsu.itindr.find.chat.model.ProfileItem

class ChatFragment : Fragment(R.layout.fragment_chat) {
    companion object {
        val TAG = ChatFragment::class.java.simpleName
        fun newInstance() = ChatFragment()
    }

    private lateinit var binding: FragmentChatBinding
    private val chatAdapterListener = object : ChatAdapter.ChatAdapterListener {
        override fun onItemClick(item: ProfileItem) {
            print(item)
        }
    }
    private val viewModel by lazy {
        ViewModelProvider(this).get(ChatViewModel::class.java)
    }
    private val chatAdapter = ChatAdapter(chatAdapterListener)

    private fun initView() = with(binding) {
        chatRecycler.apply {
            adapter = chatAdapter
            addItemDecoration(ChatItemDecoration())
        }
        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(activity, "ОШибочка чатика", Toast.LENGTH_LONG).show()
            } else {
                viewModel.isChat.observe(viewLifecycleOwner) {
                    /*val profileItems = mutableListOf<ProfileItem>()
                    if (it != null) {
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
                    }

                     */
                    if(it!=null) {
                        chatAdapter.submitList(it)
                    }
                }
            }
        }
    }

    //private var controller = GetChatController()
    val profile: MutableList<Profile> = mutableListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = SharedPreference(activity as FindActivity)
        binding = FragmentChatBinding.bind(view)
        initView()
        viewModel.getChat()

        /*controller.getChat(
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

         */
    }
}
