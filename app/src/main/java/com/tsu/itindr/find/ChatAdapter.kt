package com.tsu.itindr.find

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsu.itindr.R

class ChatAdapter:RecyclerView.Adapter<ChatAdapter.ViewHolder>(){



    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_chat,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

    }
}