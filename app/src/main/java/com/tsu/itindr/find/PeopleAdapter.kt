package com.tsu.itindr.find

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ItemChatBinding
import com.tsu.itindr.databinding.ItemPeopleBinding

class PeopleAdapter(
    private val listener: PeopleAdapterListener
) : ListAdapter<PeopleProfile, PeopleAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<PeopleProfile>() {
            override fun areItemsTheSame(oldItem: PeopleProfile, newItem: PeopleProfile) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PeopleProfile, newItem: PeopleProfile) =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_people, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPeopleBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(peopleProfile: PeopleProfile) = with(binding) {
           textViewNamePeople.text = peopleProfile.username
            peopleProfile.avatar?.let {
                imageViewAvatarPeople.load(it)
                imageViewAvatarPeople.clipToOutline= true
            }
        }
    }

    interface PeopleAdapterListener {
        fun onItemClick(item: PeopleProfile)
    }

}