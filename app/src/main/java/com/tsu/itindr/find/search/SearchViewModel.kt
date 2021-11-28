package com.tsu.itindr.find.search

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.tsu.itindr.R
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.profile.LikeController
import com.tsu.itindr.request.profile.LikeResponse
import com.tsu.itindr.request.profile.ProfileResponses
import com.tsu.itindr.request.user.UserFeedController

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    val sharedPreference = SharedPreference(app)
    val accessToken = sharedPreference.getValueString("accessToken")

    private val controller = UserFeedController()
    private val controllerLike = LikeController()

    private val _isErrorUser = MutableLiveData<Boolean>()
    val isErrorUser: LiveData<Boolean>
        get() = _isErrorUser

    private val _isUser = MutableLiveData<List<ProfileResponses>?>()
    val isUser: LiveData<List<ProfileResponses>?>
        get() = _isUser

    private val _isErrorLike = MutableLiveData<Boolean>()
    val isErrorLike: LiveData<Boolean>
        get() = _isErrorLike

    private val _isLike = MutableLiveData<LikeResponse?>()
    val isLike: LiveData<LikeResponse?>
        get() = _isLike

    private val _isErrorDisLike = MutableLiveData<Boolean>()
    val isErrorDisLike: LiveData<Boolean>
        get() = _isErrorDisLike

    fun getUser() {

        controller.feedUser(
            "Bearer " + accessToken,
            onSuccess = {
                _isErrorUser.value = false
                _isUser.value = it

            },
            onFailure = {
                _isErrorUser.value = true
            }

        )
    }

    fun disLikeProfile(userID:String) {
        controllerLike.dislikeUser(
            "Bearer " + accessToken,
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
            "Bearer " + accessToken,
            userID,
            onSuccess = {
                _isErrorLike.value=false
                _isLike.value=it
            },
            onFailure = {
                _isErrorLike.value=true
                _isLike.value=null
            }
        )
    }

}