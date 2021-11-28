package com.tsu.itindr.find.people

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tsu.itindr.R
import com.tsu.itindr.databinding.FragmentPeopleBinding
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.user.PeopleController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.find.PeopleProfile


class PeopleFragment:Fragment(R.layout.fragment_people) {
    companion object{
        val TAG = PeopleFragment::class.java.simpleName
        fun newInstance()= PeopleFragment()
    }
    private val controller = PeopleController()
    private lateinit var binding: FragmentPeopleBinding
    private val peopleAdapterListener=object : PeopleAdapter.PeopleAdapterListener {
        override fun onItemClick(item: PeopleProfile) {
            print(item)
        }
    }
    private val peopleAdapter= PeopleAdapter(peopleAdapterListener)

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
         "Bearer " + accessToken,24,50,
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