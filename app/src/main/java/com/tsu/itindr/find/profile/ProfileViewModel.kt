package com.tsu.itindr.find.profile

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.tsu.itindr.R
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.profile.ProfileController
import com.tsu.itindr.request.profile.ProfileResponses

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    val sharedPreference = SharedPreference(app)
    val accessToken = sharedPreference.getValueString("accessToken")


    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val _isUser = MutableLiveData<ProfileResponses?>()
    val isUser: LiveData<ProfileResponses?>
        get() = _isUser

    private val controller = ProfileController()

    fun getProfile() {
        controller.profile(
            "Bearer " + accessToken,
            onSuccess =
            {
                _isErrorUser.value=false
                _isUser.value=it
            },
            onFailure =
            {
               _isErrorUser.value=true
            }
        )
    }
}