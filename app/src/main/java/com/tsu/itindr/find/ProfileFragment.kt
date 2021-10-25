package com.tsu.itindr.find

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tsu.itindr.R
import com.tsu.itindr.databinding.FragmentProfileBinding
import com.tsu.itindr.edit.EditActivity


class ProfileFragment: Fragment(R.layout.fragment_profile) {
    companion object{
        val TAG = ProfileFragment::class.java.simpleName
        fun newInstance()= ProfileFragment()
    }
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        val intent= Intent(activity,EditActivity::class.java)

        binding.button4.setOnClickListener { startActivity(intent) }
    }

}