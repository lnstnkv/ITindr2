package com.tsu.itindr.find

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tsu.itindr.R
import com.tsu.itindr.databinding.FragmentChatBinding
import com.tsu.itindr.databinding.FragmentPeopleBinding
import com.tsu.itindr.databinding.FragmentProfileBinding
import com.tsu.itindr.edit.EditActivity
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.profile.ProfileController
import com.tsu.itindr.request.user.PeopleController
import com.tsu.itindr.request.user.UserController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class PeopleFragment:Fragment(R.layout.fragment_people) {
    companion object{
        val TAG = PeopleFragment::class.java.simpleName
        fun newInstance()= PeopleFragment()
    }
    private val controller = PeopleController()
    private lateinit var binding: FragmentPeopleBinding
    private val peopleAdapterListener=object : PeopleAdapter.PeopleAdapterListener{
        override fun onItemClick(item: PeopleProfile) {
            print(item)
        }
    }
    private val peopleAdapter=PeopleAdapter(peopleAdapterListener)

    private fun initView()= with(binding){
        peopleRecycler.layoutManager = StaggeredGridLayoutManager( 3,StaggeredGridLayoutManager.VERTICAL)
        peopleRecycler.apply {
            adapter=peopleAdapter
            addItemDecoration(PeopleItemDecoration())
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = SharedPreference(activity as FindActivity)
        val accessToken=sharedPreference.getValueString("accessToken")
        binding = FragmentPeopleBinding.bind(view)
        initView()
     controller.getUser(
         "Bearer " + accessToken,24,100,
         onSuccess = {
             val peopleItems= mutableListOf<PeopleProfile>()
             for (getChat in it) {
                 peopleItems.add(
                     PeopleProfile(
                         id = getChat.userId,
                         username = getChat.name,
                         avatar = getChat.avatar
                     )
                 )
             }
             peopleAdapter.submitList(peopleItems)
             Toast.makeText(activity, "Все прекрасно", Toast.LENGTH_LONG).show()
         },
         onFailure = {
             Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
         }
     )
    }
}