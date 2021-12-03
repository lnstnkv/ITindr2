package com.tsu.itindr.find.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ItemPeopleBinding
import com.tsu.itindr.find.people.model.PeopleProfile

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
                Glide
                    .with(imageViewAvatarPeople.context)
                    .load(peopleProfile.avatar)
                    .circleCrop()
                    .into(imageViewAvatarPeople)
            }
        }
    }

    interface PeopleAdapterListener {
        fun onItemClick(item: PeopleProfile)
    }

}