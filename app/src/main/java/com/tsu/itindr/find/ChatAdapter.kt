package com.tsu.itindr.find

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ItemChatBinding

class ChatAdapter(
    private val listener: ChatAdapterListener
) :  ListAdapter<ProfileItem,ChatAdapter.ViewHolder>(DIFF){

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<ProfileItem>() {
            override fun areItemsTheSame(oldItem: ProfileItem, newItem: ProfileItem) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProfileItem, newItem: ProfileItem) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChatBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }
        fun bind(profileItem: ProfileItem)= with(binding){
           profileItem.username= textViewNameUser.text as String
           profileItem.lastMessage?.let { textViewLasMessenger.text=it }
           profileItem.avatar?.let {
               imageViewUserChat.load(it)
               imageViewUserChat.clipToOutline=true
           }
        }
    }
    interface ChatAdapterListener{
        fun onItemClick(item: ProfileItem)
    }
}