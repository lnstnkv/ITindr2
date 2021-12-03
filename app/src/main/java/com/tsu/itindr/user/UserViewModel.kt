package com.tsu.itindr.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.data.profile.LikeController
import com.tsu.itindr.data.profile.LikeResponse
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.room.people.ProfileRepository
import kotlinx.coroutines.launch

class UserViewModel(app: Application) : AndroidViewModel(app) {

    private val _isLike = MutableLiveData<LikeResponse?>()
    val isLike: LiveData<LikeResponse?>
        get() = _isLike

    private val _isErrorDisLike = MutableLiveData<Boolean>()
    val isErrorDisLike: LiveData<Boolean>
        get() = _isErrorDisLike

    private val _isErrorLike = MutableLiveData<Boolean>()
    val isErrorLike: LiveData<Boolean>
        get() = _isErrorLike

    private val controllerLike = LikeController(app)
    fun disLikeProfile(userID: String) {
        controllerLike.dislikeUser(
            userID,
            onSuccess = {
                _isErrorDisLike.value = false

            },
            onFailure = {
                _isErrorDisLike.value = true
            }
        )
    }

    fun likeProfile(userID: String) {
        controllerLike.likeUser(
            userID,
            onSuccess = {
                _isErrorLike.value = false
                _isLike.value = it
            },
            onFailure = {
                _isErrorLike.value = true
                _isLike.value = null
            }
        )
    }

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