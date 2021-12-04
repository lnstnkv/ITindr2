package com.tsu.itindr.find.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.profile.ProfileController
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.room.people.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val profileRepository = ProfileRepository(app)

    private val _userId = MutableLiveData<PeopleProfile>()
    val userId: LiveData<PeopleProfile>
        get() = _userId

    private val controller = ProfileController(app)

    init {
        viewModelScope.launch {
            if (!profileRepository.checkProfile()) {
                getProfile()
            }
            else
            {
                getMyProfile()
            }
        }
    }


    fun getProfile() {
        controller.profile(
            onSuccess =
            {
                _isErrorUser.value = false
                add(it)
            },
            onFailure =
            {
                _isErrorUser.value = true
            }
        )
    }

    fun add(profile: ProfileResponses) {
        viewModelScope.launch {
            profileRepository.addNewUser(profile.toEntityData())
            getMyProfile()
        }
    }

    fun getMyProfile() {
        viewModelScope.launch {
            _userId.value = profileRepository.observeMyProfile()
        }
    }
}