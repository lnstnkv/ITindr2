package com.tsu.itindr.find

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tsu.itindr.*
import com.tsu.itindr.databinding.FragmentSearchBinding
import com.tsu.itindr.request.profile.LikeController
import com.tsu.itindr.request.profile.ProfileResponses
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.user.UserFeedController

class SearchFragment : Fragment(R.layout.fragment_search) {
    companion object {
        val TAG = SearchFragment::class.java.simpleName
        fun newInstance() = SearchFragment()
    }

    private lateinit var chipGroup: ChipGroup
    private val controller = UserFeedController()
    private val controllerLike = LikeController()
    var users: List<ProfileResponses> = listOf()
    var index: Int = 0
    var indexId: Int = 0
    var userID: String = ""
    val sharedPreference = SharedPreference(activity as FindActivity)
    val accessToken = sharedPreference.getValueString("accessToken")
    private lateinit var viewbinding: FragmentSearchBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewbinding = FragmentSearchBinding.bind(view)
        chipGroup = viewbinding.chipGroupSearch
        viewbinding.imageViewAvatarSearch.clipToOutline = true

        getUser()

        viewbinding.buttonClose.setOnClickListener {
            disLikeProfile()
            addResponse()
        }
        viewbinding.buttonLike.setOnClickListener {

            likeProfile()
            addResponse()
        }

    }

    private fun getUser() {
        controller.feedUser(
            "Bearer " + accessToken,
            onSuccess = {
                users = it

                viewbinding.textViewNameFeed.text = users[index].name
                for (j in users[index].topics) {
                    addChip(j.title)
                }
                viewbinding.textViewAbout.text = users[index].aboutMyself
                Glide
                    .with(this)
                    .load(users[index].avatar)
                    .into(viewbinding.imageViewAvatarSearch);

                userID = users[index].userId
            },
            onFailure = {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            }

        )
    }

    private fun disLikeProfile() {
        controllerLike.dislikeUser(
            "Bearer " + accessToken,
            userID,
            onSuccess = {
                Toast.makeText(activity, "Как жаль, что он вам не подошел!", Toast.LENGTH_LONG)
                    .show()
            },
            onFailure = {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun likeProfile() {
        controllerLike.likeUser(
            "Bearer " + accessToken,
            userID,
            onSuccess = {
                if (it.isMutual) {
                    val intent = Intent(activity, MatchActivity::class.java)
                    startActivity(intent)
                }

            },
            onFailure = {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            }

        )
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