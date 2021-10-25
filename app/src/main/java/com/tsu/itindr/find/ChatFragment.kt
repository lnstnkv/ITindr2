package com.tsu.itindr.find

import androidx.fragment.app.Fragment
import com.tsu.itindr.R

class ChatFragment:Fragment(R.layout.fragment_chat) {
    companion object{
        val TAG = ChatFragment::class.java.simpleName
        fun newInstance()= ChatFragment()
    }
}