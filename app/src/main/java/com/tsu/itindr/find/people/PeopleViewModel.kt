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

    val sharedPreference = SharedPreference(app)
    val accessToken = sharedPreference.getValueString("accessToken")

    private val profileRepository = ProfileRepository(app)
    val profiles = profileRepository.observeAllProfiles()

    private val controller = PeopleController()

    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val _isUser = MutableLiveData<List<PeopleProfile>?>()
    val isUser: LiveData<List<PeopleProfile>?>
        get() = _isUser

    fun getUser() {
        controller.getUser(
            "Bearer " + accessToken, 24, 50,
            onSuccess = {
                _isErrorUser.value = false
                val peopleItems = mutableListOf<PeopleProfile>()
                for (getChat in it) {
                    peopleItems.add(
                        PeopleProfile(
                            id = getChat.userId,
                            username = getChat.name,
                            avatar = getChat.avatar
                        )
                    )
                   add(getChat)



                }

                _isUser.value = peopleItems
            },
            onFailure = {
                _isErrorUser.value = true
            }
        )
    }

    fun add(profile:ProfileResponses) {
        viewModelScope.launch {
           profileRepository.addNew(id=profile.userId,name = profile.name.toString(),avatar = profile.avatar.toString())
        }
    }


}