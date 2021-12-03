package com.tsu.itindr.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.data.user.PeopleController
import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.room.people.ProfileEntity
import com.tsu.itindr.room.people.ProfileRepository
import kotlinx.coroutines.launch

class ProfileActivityViewModel(app: Application) : AndroidViewModel(app) {

    private val profileRepository = ProfileRepository(app)

    private val _userId = MutableLiveData<PeopleProfile>()
    val userId: LiveData<PeopleProfile>
        get() = _userId


    fun getProfile(userId: String) {
        viewModelScope.launch {
            _userId.value=profileRepository.observeProfile(userId)
        }
    }
}