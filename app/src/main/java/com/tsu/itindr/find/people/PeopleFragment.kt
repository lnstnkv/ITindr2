package com.tsu.itindr.find.people

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.R
import com.tsu.itindr.databinding.FragmentPeopleBinding
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tsu.itindr.chat.ChatActivity
import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.profile.ProfileActivity


class PeopleFragment : Fragment(R.layout.fragment_people) {
    companion object {
        val TAG = PeopleFragment::class.java.simpleName
        fun newInstance() = PeopleFragment()
    }

    private lateinit var binding: FragmentPeopleBinding

    private val viewModel by viewModels<PeopleViewModel>()

    private val peopleAdapterListener = object : PeopleAdapter.PeopleAdapterListener {
        override fun onItemClick(item: PeopleProfile) {
            val intent =
                Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    private val peopleAdapter = PeopleAdapter(peopleAdapterListener)

    private fun initView() = with(binding) {

        viewModel.isErrorUser.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            }
        }


        peopleRecycler.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        peopleRecycler.apply {
            adapter = peopleAdapter
            addItemDecoration(PeopleItemDecoration())
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPeopleBinding.bind(view)
        initView()
        viewModel.getUser()
        viewModel.profiles.observe(viewLifecycleOwner) {
            peopleAdapter.submitList(it)
        }
    }
}