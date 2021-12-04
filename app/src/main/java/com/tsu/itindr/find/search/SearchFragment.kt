package com.tsu.itindr.find.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tsu.itindr.*
import com.tsu.itindr.databinding.FragmentSearchBinding
import com.tsu.itindr.find.MatchActivity
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.profile.ProfileActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SearchFragment : Fragment(R.layout.fragment_search) {
    companion object {
        val TAG = SearchFragment::class.java.simpleName
        fun newInstance() = SearchFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private lateinit var chipGroup: ChipGroup

    var users: List<ProfileResponses> = listOf()
    var index: Int = 0
    var userID: String = ""

    private lateinit var viewbinding: FragmentSearchBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewbinding = FragmentSearchBinding.bind(view)
        chipGroup = viewbinding.chipGroupSearch
        viewbinding.imageViewAvatarSearch.clipToOutline = true
        initView()
        viewModel.getUser()
        viewbinding.buttonClose.setOnClickListener {
            viewModel.disLikeProfile(userID)
            addResponse()
        }
        viewbinding.buttonLike.setOnClickListener {
            viewModel.likeProfile(userID)
            addResponse()
        }


    }

    private fun initView() = with(viewbinding) {
        viewModel.isErrorUser.observe(viewLifecycleOwner) { isError ->
            if (isError == true) {
                Toast.makeText(activity, R.string.error_email, Toast.LENGTH_LONG)
                    .show()
            }

        }
        viewModel.userProfile.observe(viewLifecycleOwner) {
            it?.let {
                users = it
                viewbinding.textViewNameFeed.text = users[index].name
                for (j in users[index].topics) {
                    addChip(j.title)
                }
                viewbinding.textViewAbout.text = users[index].aboutMyself
                Glide
                    .with(imageViewAvatarSearch.context)
                    .load(users[index].avatar)
                    .into(viewbinding.imageViewAvatarSearch);

                userID = users[index].userId

            }
            viewbinding.imageViewAvatarSearch.setOnClickListener {
                val intent = Intent(activity, ProfileActivity::class.java)
                intent.putExtra("userId", userID)
                intent.putExtra("name", users[index].name)
                intent.putExtra("about", users[index].aboutMyself)
                intent.putExtra("topics", Json.encodeToString(users[index].topics))
                intent.putExtra("avatar", users[index].avatar)
                Log.d("userID", userID)
                startActivity(intent)
            }
        }
        viewModel.isErrorDisLike.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(activity, "Как жаль, что он вам не подошел!", Toast.LENGTH_LONG)
                    .show()
            }
        }
        viewModel.isErrorLike.observe(viewLifecycleOwner) { isMutual ->
            if (isMutual == false) {
                viewModel.isMutual.observe(viewLifecycleOwner) {
                    if (it != null) {
                        if (it.isMutual) {
                            val intent = Intent(activity, MatchActivity::class.java)
                            startActivity(intent)
                        }
                    }

                }
            } else {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addResponse() {
        index++
        chipGroup.removeAllViews()
        userID = users[index].userId
        viewbinding.textViewNameFeed.text = users[index].name
        for (j in users[index].topics) {
            addChip(j.title)
        }
        viewbinding.textViewAbout.text = users[index].aboutMyself
        Glide
            .with(this)
            .load(users[index].avatar)
            .into(viewbinding.imageViewAvatarSearch)
    }

    private fun addChip(text: String) {

        val chip = LayoutInflater.from(activity).inflate(R.layout.item_chip_pink, null) as Chip
        chip.text = text
        chipGroup.addView(chip)
    }
}