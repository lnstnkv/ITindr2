package com.tsu.itindr.profile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.tsu.itindr.R
import com.tsu.itindr.authorization.AuthorizationViewModel
import com.tsu.itindr.data.TopicItem
import com.tsu.itindr.data.profile.TopicResponse
import com.tsu.itindr.databinding.ActivityAuthorizationBinding
import com.tsu.itindr.databinding.ActivityProfileBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ProfileActivity : AppCompatActivity() {
    private val viewbinding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(this).get(ProfileActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewbinding.root)
        initView()
        viewbinding.materialToolbar.setOnClickListener { finish() }

    }

    fun initView() = with(viewbinding) {
        val fName = intent.getStringExtra("userId")
        val about = intent.getStringExtra("about")
        val name = intent.getStringExtra("name")
        val avatar = intent.getStringExtra("avatar")
        val topic = intent.getStringExtra("topics")
        var topics = listOf<TopicResponse>()
        if (!topic.isNullOrBlank()) {
            topics = Json.decodeFromString(topic)
        }

        if (!fName.isNullOrBlank()) {
            viewModel.getProfile(fName)
            viewModel.userId.observe(this@ProfileActivity) { profile ->
                viewbinding.textViewNameUserProfile.text = profile.username.toString()
                profile.avatar?.let {
                    Glide
                        .with(viewbinding.imageViewProfile)
                        .load(profile.avatar)
                        .into(viewbinding.imageViewProfile)
                }
                viewbinding.textViewAboutUser.text = profile.about.toString()
                for (i in profile.topics.map { item -> item.toDomainSerialization() }) {
                    addChip(i)
                }
            }
        } else {
            viewbinding.textViewNameUserProfile.text = name.toString()
            avatar?.let {
                Glide
                    .with(viewbinding.imageViewProfile)
                    .load(avatar)
                    .into(viewbinding.imageViewProfile)
            }
            viewbinding.textViewAboutUser.text = about.toString()

            for (i in topics) {
                addChip(i)
            }

        }

    }


    private fun addChip(it: TopicResponse) {
        val text = it.title
        val chipChoose = LayoutInflater.from(this).inflate(R.layout.item_chip_pink, null) as Chip
        chipChoose.text = text
        viewbinding.chipGroup2.addView(chipChoose)
    }


}