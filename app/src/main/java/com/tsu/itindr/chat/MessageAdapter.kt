package com.tsu.itindr.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsu.itindr.R
import com.tsu.itindr.chat.model.Message
import com.tsu.itindr.databinding.ItemChatBinding
import com.tsu.itindr.databinding.ItemChatMessengerBinding

class MessageAdapter (
    private val listener: MessageAdapterListener
) :  ListAdapter<Message, MessageAdapter.ViewHolder>(DIFF){

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_chat_messenger, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChatMessengerBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }
        fun bind(profileItem: Message)= with(binding){

            profileItem.createdAt?.let { messageDateText.text=it }
            profileItem.text?.let { messageText.text=it }
            profileItem.avatar?.let {
                Glide
                    .with(imageViewUserChat.context)
                    .load(profileItem.avatar)
                    .circleCrop()
                    .into(imageViewUserChat)
                //imageViewUserChat.load(it)
                imageViewUserChat.clipToOutline=true
            }
        }
    }
    interface MessageAdapterListener{
        fun onItemClick(item: Message)
    }
}