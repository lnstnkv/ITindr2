package com.tsu.itindr.profile

import android.content.Intent
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

class ProfileActivity : AppCompatActivity() {
    private val viewbinding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(this).get(ProfileActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewbinding.root)
        initView()

    }

    fun initView() = with(viewbinding) {
        val fName = intent.getStringExtra("userId")
        fName?.let { viewModel.getProfile(it) }
        viewModel.userId.observe(this@ProfileActivity) { profile ->
            viewbinding.textView2.text= profile.about.toString()
            profile.avatar?.let {
                Glide
                    .with(viewbinding.imageViewProfile)
                    .load(profile.avatar)
                    .into(viewbinding.imageViewProfile)
            }
           viewbinding.textView.text = profile.username.toString()
            for (i in profile.topics) {
                addChip(i)
            }
        }
    }


    private fun addChip(it: TopicItem) {
        val text = it.title
        val id = it.id
        val chipChoose = LayoutInflater.from(this).inflate(R.layout.item_chip, null) as Chip
        chipChoose.text = text
        chipChoose.isClickable = true
        chipChoose.isCheckable = true
        chipChoose.setChipBackgroundColorResource(R.color.pink)
        viewbinding.chipGroup2.addView(chipChoose)
    }


}