package com.tsu.itindr.find

import androidx.fragment.app.Fragment
import com.tsu.itindr.R

class SearchFragment:Fragment(R.layout.fragment_search) {
    companion object{
        val TAG = SearchFragment::class.java.simpleName
        fun newInstance()= SearchFragment()
    }
}