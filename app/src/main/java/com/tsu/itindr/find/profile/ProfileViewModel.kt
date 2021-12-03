package com.tsu.itindr.find.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.profile.ProfileController
import com.tsu.itindr.data.profile.ProfileResponses

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    val sharedPreference = SharedPreference(app)
    val accessToken = sharedPreference.getValueString("accessToken")


    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val _userItem = MutableLiveData<ProfileResponses?>()
    val userItem: LiveData<ProfileResponses?>
        get() = _userItem

    private val controller = ProfileController(app)

    fun getProfile() {
        controller.profile(
            onSuccess =
            {
                _isErrorUser.value=false
                _userItem.value=it
            },
            onFailure =
            {
               _isErrorUser.value=true
            }
        )
    }
}