package com.tsu.itindr.find

import androidx.fragment.app.Fragment
import com.tsu.itindr.R


class ProfileFragment: Fragment(R.layout.fragment_profile) {
    companion object{
        val TAG = ProfileFragment::class.java.simpleName
        fun newInstance()= ProfileFragment()
    }

}