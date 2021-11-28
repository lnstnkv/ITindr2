package com.tsu.itindr.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.itindr.databinding.ActivityMatchBinding
import com.tsu.itindr.find.chat.ChatFragment
import com.tsu.itindr.request.SharedPreference

class MatchActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMatchBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)
        viewBinding.buttonCreateMessage.setOnClickListener {
            val intent = Intent(this@MatchActivity, ChatFragment::class.java)
            startActivity(intent)
        }
    }
}