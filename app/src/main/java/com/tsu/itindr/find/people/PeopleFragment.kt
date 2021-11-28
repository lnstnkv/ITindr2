package com.tsu.itindr.find.people

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.R
import com.tsu.itindr.databinding.FragmentPeopleBinding
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.user.PeopleController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.find.PeopleProfile
import com.tsu.itindr.find.search.SearchViewModel


class PeopleFragment : Fragment(R.layout.fragment_people) {
    companion object {
        val TAG = PeopleFragment::class.java.simpleName
        fun newInstance() = PeopleFragment()
    }

    private lateinit var binding: FragmentPeopleBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(PeopleViewModel::class.java)
    }

    private val peopleAdapterListener = object : PeopleAdapter.PeopleAdapterListener {
        override fun onItemClick(item: PeopleProfile) {
            print(item)
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

    private fun addRecycler() {
        viewModel.isUser.observe(viewLifecycleOwner) {
            val peopleItems = mutableListOf<PeopleProfile>()
            if (it != null) {
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
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPeopleBinding.bind(view)
        initView()
        viewModel.getUser()
        addRecycler()
    }
}