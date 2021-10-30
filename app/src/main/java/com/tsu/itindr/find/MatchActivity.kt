package com.tsu.itindr.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivityFindBinding
import com.tsu.itindr.databinding.ActivityMatchBinding
import com.tsu.itindr.request.SharedPreference

class MatchActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMatchBinding.inflate(layoutInflater)
        val sharedPreference = SharedPreference(this)
        setContentView(viewBinding.root)
        viewBinding.buttonCreateMessage.setOnClickListener {
            val intent = Intent(this@MatchActivity, ChatFragment::class.java)
            startActivity(intent)
        }
    }
}