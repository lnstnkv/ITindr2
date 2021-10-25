package com.tsu.itindr.find

import androidx.fragment.app.Fragment
import com.tsu.itindr.R

class PeopleFragment:Fragment(R.layout.fragment_people) {
    companion object{
        val TAG = PeopleFragment::class.java.simpleName
        fun newInstance()= PeopleFragment()
    }
}