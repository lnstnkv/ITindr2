package com.tsu.itindr.find.people

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsu.itindr.R
import com.tsu.itindr.find.PeopleProfile
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.profile.ProfileResponses
import com.tsu.itindr.request.user.PeopleController

class PeopleViewModel(app: Application) : AndroidViewModel(app) {

    val sharedPreference = SharedPreference(app)
    val accessToken = sharedPreference.getValueString("accessToken")

    private val controller = PeopleController()

    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val _isUser = MutableLiveData<List<ProfileResponses>?>()
    val isUser: LiveData<List<ProfileResponses>?>
        get() = _isUser

    fun getUser() {
        controller.getUser(
            "Bearer " + accessToken, 24, 50,
            onSuccess = {
            _isErrorUser.value=false
            _isUser.value=it

            },
            onFailure = {
                _isErrorUser.value=true
            }
        )
    }
}