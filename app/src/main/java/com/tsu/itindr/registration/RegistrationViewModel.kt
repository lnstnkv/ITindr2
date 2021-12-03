package com.tsu.itindr.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.itindr.registration.model.Email
import com.tsu.itindr.registration.model.RegisterController
import com.tsu.itindr.registration.model.RegisterParams
import com.tsu.itindr.registration.model.RegisterResponse

class RegistrationViewModel (app: Application) : AndroidViewModel(app) {

    private val controller = RegisterController(app)
    private val _isErrorEmail = MutableLiveData<Boolean>()
    val isErrorEmail: LiveData<Boolean>
        get() = _isErrorEmail

    private val _isErrorSamePassword = MutableLiveData<Boolean>()
    val isErrorSamePassword: LiveData<Boolean>
        get() = _isErrorSamePassword
    private val _isErrorPassword = MutableLiveData<Boolean>()
    val isErrorPassword: LiveData<Boolean>
        get() = _isErrorPassword

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean>
        get() = _registerSuccess

    private val checkingEmail = Email()

    private fun checkEmail(email: String) {

        _isErrorEmail.value = checkingEmail.checkEmail(email)
    }

    private fun samePassword(password: String, passwordRepeat: String) {
        _isErrorSamePassword.value = passwordRepeat.contentEquals(password)
    }

    fun register(email: String, password: String, passwordRepeat: String) {
        checkEmail(email)
        samePassword(password, passwordRepeat)
        if (_isErrorEmail.value == false || _isErrorSamePassword.value == false) return
        controller.register(
            RegisterParams(email, password),
            onSuccess = {

                _registerSuccess.value = true

            },
            onFailure = {
                _registerSuccess.value = false
            })
    }

}