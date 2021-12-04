package com.tsu.itindr.find.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.profile.LikeController
import com.tsu.itindr.data.profile.LikeResponse
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.data.user.UserFeedController

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    private val controller = UserFeedController(app)
    private val controllerLike = LikeController(app)

    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val _userProfile = MutableLiveData<List<ProfileResponses>?>()
    val userProfile: LiveData<List<ProfileResponses>?>
        get() = _userProfile

    private val _isErrorLike = MutableLiveData<Boolean>()
    val isErrorLike: LiveData<Boolean>
        get() = _isErrorLike

    private val _isMutual = MutableLiveData<LikeResponse?>()
    val isMutual: LiveData<LikeResponse?>
        get() = _isMutual

    private val _isErrorDisLike = MutableLiveData<Boolean>()
    val isErrorDisLike: LiveData<Boolean>
        get() = _isErrorDisLike

    fun getUser() {

        controller.feedUser(
            onSuccess = {
                _isErrorUser.value = false
                _userProfile.value = it

            },
            onFailure = {
                _isErrorUser.value = true
            }

        )
    }

    fun disLikeProfile(userID:String) {
        controllerLike.dislikeUser(
            userID,
            onSuccess = {
            _isErrorDisLike.value=false

            },
            onFailure = {
                _isErrorDisLike.value=true
            }
        )
    }
    fun likeProfile(userID:String) {
        controllerLike.likeUser(
            userID,
            onSuccess = {
                _isErrorLike.value=false
                _isMutual.value=it
            },
            onFailure = {
                _isErrorLike.value=true
                _isMutual.value=null
            }
        )
    }

}