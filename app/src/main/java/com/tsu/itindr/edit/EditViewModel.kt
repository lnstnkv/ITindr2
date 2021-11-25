package com.tsu.itindr.edit

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.tsu.itindr.R
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.avatar.AvatarController
import com.tsu.itindr.request.profile.ProfileController
import com.tsu.itindr.request.profile.ProfileResponses
import com.tsu.itindr.request.profile.TopicController
import com.tsu.itindr.request.profile.TopicResponse

class EditViewModel(app: Application) : AndroidViewModel(app) {

    private val context = app.applicationContext
    val sharedPreference = SharedPreference(context)

    private val saveAvatar = AvatarController()
    private val controllerTopic = TopicController()
    private var controller = ProfileController()

    private val _isErrorTopic = MutableLiveData<List<TopicResponse>?>()
    val isErrorTopic: LiveData<List<TopicResponse>?>
        get() = _isErrorTopic

    private val _isErrorAvatar = MutableLiveData<Boolean>()
    val isErrorAvatar: LiveData<Boolean>
        get() = _isErrorAvatar

    private val _isErrorProfile = MutableLiveData<ProfileResponses?>()
    val isErrorProfile: LiveData<ProfileResponses?>
        get() = _isErrorProfile

    val accessToken = sharedPreference.getValueString("accessToken")


    fun deleteAvatar() {


        saveAvatar.deleteAvatar("Bearer " + accessToken,
            onSuccess = {
                _isErrorAvatar.value = false
            },
            onFailure = {
                _isErrorAvatar.value = true
            })

    }

    fun addTopic() {

        controllerTopic.topic(
            "Bearer " + accessToken,
            onSuccess = {
                _isErrorTopic.value = it

            },
            onFailure = {
                _isErrorTopic.value = null
            }

        )

    }
    fun getProfile() {
        controller.profile(
            "Bearer " + accessToken,
            onSuccess = {
                _isErrorProfile.value=it

            },
            onFailure = {
                _isErrorProfile.value=null
            }
        )
    }

}