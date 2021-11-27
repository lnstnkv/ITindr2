package com.tsu.itindr.find.search

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.tsu.itindr.R
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.profile.ProfileResponses
import com.tsu.itindr.request.user.UserFeedController

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    val sharedPreference = SharedPreference(app)
    val accessToken = sharedPreference.getValueString("accessToken")
    private val controller = UserFeedController()

    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val _isUser = MutableLiveData<List<ProfileResponses>?>()
    val isUser: LiveData<List<ProfileResponses>?>
        get() = _isUser

    fun getUser() {

        controller.feedUser(
            "Bearer " + accessToken,
            onSuccess = {
                _isErrorUser.value = false
                _isUser.value = it

            },
            onFailure = {
                _isErrorUser.value = true
            }

        )
    }

}