package com.tsu.itindr.profile

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
import kotlinx.coroutines.launch

class ProfileActivityViewModel(app: Application) : AndroidViewModel(app) {   val sharedPreference = SharedPreference(app)
    val accessToken = sharedPreference.getValueString("accessToken")

    private val profileRepository = ProfileRepository(app)
    val profiles = profileRepository.observeAllProfiles()

    private val controller = PeopleController(app)

    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val _isUser = MutableLiveData<List<PeopleProfile>?>()
    val isUser: LiveData<List<PeopleProfile>?>
        get() = _isUser


    /*fun add(profile: List<ProfileResponses>) {
        viewModelScope.launch {
            profileRepository.addNew(id=profile.userId,name = profile.name.toString(),avatar = profile.avatar.toString())
        }
    }

     */
}