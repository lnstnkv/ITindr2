package com.tsu.itindr.edit

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.avatar.AvatarController
import com.tsu.itindr.request.profile.*
import com.tsu.itindr.request.user.UserController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class EditViewModel(app: Application) : AndroidViewModel(app) {

    val sharedPreference = SharedPreference(app)
    private val updateController = UserController()
    private val saveAvatar = AvatarController()
    private val controllerTopic = TopicController()
    private var controller = ProfileController()

    private val _isErrorFromTopic = MutableLiveData<Boolean>()
    val isErrorFromTopic:  LiveData<Boolean>
        get() = _isErrorFromTopic
    private val _isTopic = MutableLiveData<List<TopicResponse>?>()
    val isTopic: LiveData<List<TopicResponse>?>
        get() = _isTopic

    private val _isErrorAvatar = MutableLiveData<Boolean>()
    val isErrorAvatar: LiveData<Boolean>
        get() = _isErrorAvatar

    private val _isErrorProfile = MutableLiveData<ProfileResponses?>()
    val isErrorProfile: LiveData<ProfileResponses?>
        get() = _isErrorProfile


    private val _isErrorUpdateProfile = MutableLiveData<Boolean>()
    val isErrorUpdateProfile: LiveData<Boolean>
        get() = _isErrorUpdateProfile

    private val _isErrorSaveAvatar = MutableLiveData<Boolean>()
    val isErrorSaveAvatar: LiveData<Boolean>
        get() = _isErrorSaveAvatar


   // private val multiPartAvatar = Avatar()
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
                _isErrorFromTopic.value=false
                _isTopic.value = it

            },
            onFailure = {
                _isErrorFromTopic.value=true
                _isTopic.value = null
            }

        )

    }

    /*fun saveAvatar(uri: Uri) {

        saveAvatar.updateAvatar(
            "Bearer " + accessToken,
            multiPartAvatar.saveAvatarFunc(uri),
            onSuccess = {
                _isErrorSaveAvatar.value = false

            },
            onFailure = {
                _isErrorSaveAvatar.value = true

            })

    }

     */

    fun getProfile() {
        controller.profile(
            "Bearer " + accessToken,
            onSuccess = {
                _isErrorProfile.value = it

            },
            onFailure = {
                _isErrorProfile.value = null
            }
        )
    }

    fun updateProfile(name: String, aboutMyself: String, topics: List<String>) {

        updateController.update(
            "Bearer " + accessToken,
            UpdateParams(
                name,
                aboutMyself,
                topics
            ),
            onSuccess = {
                _isErrorUpdateProfile.value = false

            },
            onFailure = {
                _isErrorUpdateProfile.value = true

            }
        )
    }
}