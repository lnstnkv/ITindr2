package com.tsu.itindr.find.people

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.data.user.PeopleController
import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.room.people.ProfileRepository
//import com.tsu.itindr.room.people.ProfileRepository
import kotlinx.coroutines.launch

class PeopleViewModel(app: Application) : AndroidViewModel(app) {

    private val profileRepository = ProfileRepository(app)
    val profiles = profileRepository.observeAllProfiles()

    private val controller = PeopleController(app)

    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    fun getUser() {
        controller.getUser(
            32, 0,
            onSuccess = {
                _isErrorUser.value = false
                add(it)
            },
            onFailure = {
                _isErrorUser.value = true
            }
        )
    }

    fun add(profile: List<ProfileResponses>) {
        viewModelScope.launch {
            profileRepository.addNew(profile.map { it.toEntityData() })
        }
    }

}