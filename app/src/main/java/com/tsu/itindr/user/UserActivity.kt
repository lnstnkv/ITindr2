package com.tsu.itindr.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.tsu.itindr.R
import com.tsu.itindr.data.TopicItem
import com.tsu.itindr.databinding.ActivityUserBinding
import com.tsu.itindr.find.MatchActivity

class UserActivity : AppCompatActivity() {

    private val viewbinding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewbinding.root)
        initView()
        val fName = intent.getStringExtra("userId")
        fName?.let { viewModel.getProfile(it) }
        viewbinding.buttonCloseUser.setOnClickListener {
            fName?.let { it1 -> viewModel.disLikeProfile(it1) }
        }
        viewbinding.buttonLikeUser.setOnClickListener {
            fName?.let { it1 -> viewModel.likeProfile(it1) }
        }
        viewbinding.toolbarControls.setOnClickListener { finish() }

    }

    private fun initView() = with(viewbinding) {
        viewModel.userItem.observe(this@UserActivity){ profile->
            if(profile!=null){
                viewbinding.textViewNameProfile.text=profile.username
                viewbinding.textViewAboutMySelf.text=profile.about
                profile.avatar?.let {
                    Glide
                        .with(viewbinding.imageViewAvatarProfile)
                        .load(profile.avatar)
                        .circleCrop()
                        .into(viewbinding.imageViewAvatarProfile)
                }
                for(i in profile.topics){
                    addChip(i)
                }
            }
        }
        viewModel.isErrorDisLike.observe(this@UserActivity) {
            if (it == true) {
                Toast.makeText(this@UserActivity, R.string.error, Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(
                    this@UserActivity,
                    "Как жаль, что он вам не подошел!",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
        viewModel.isErrorLike.observe(this@UserActivity) { it ->
            if (it == false) {
                viewModel.isLike.observe(this@UserActivity) {
                    if (it != null) {
                        if (it.isMutual) {
                            val intent = Intent(this@UserActivity, MatchActivity::class.java)
                            startActivity(intent)
                        }
                    }

                }
            } else {
                Toast.makeText(this@UserActivity, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun addChip(it: TopicItem) {
        val text = it.title
        val id = it.id
        val chipChoose = LayoutInflater.from(this).inflate(R.layout.item_chip_pink, null) as Chip
        chipChoose.text = text
        viewbinding.chipGroupProfile.addView(chipChoose)
    }

}