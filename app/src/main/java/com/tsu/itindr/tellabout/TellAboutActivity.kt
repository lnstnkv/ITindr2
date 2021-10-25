package com.tsu.itindr.tellabout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.databinding.ActivityTellAboutBinding

class TellAboutActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityTellAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityTellAboutBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
    }
}