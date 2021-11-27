package com.tsu.itindr.authorization

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.itindr.authorization.model.LoginController
import com.tsu.itindr.authorization.model.LoginParams
import com.tsu.itindr.authorization.model.LoginResponse
import com.tsu.itindr.registration.model.Email
import com.tsu.itindr.request.SharedPreference

class AuthorizationViewModel  (app: Application) : AndroidViewModel(app)  {

    private val controller = LoginController(app)

    private val _isErrorEmail = MutableLiveData<Boolean>()
    val isErrorEmail: LiveData<Boolean>
        get() = _isErrorEmail


    private val _isErrorPassword = MutableLiveData<Boolean>()
    val isErrorPassword: LiveData<Boolean>
        get() = _isErrorPassword

    private val _authorizationSuccess = MutableLiveData<LoginResponse?>()
    val authorizationSuccess: LiveData<LoginResponse?>
        get() = _authorizationSuccess


    private val checkingEmail = Email()

    private fun checkEmail(email: String) {

        _isErrorEmail.value = checkingEmail.checkEmail(email)
    }

    private fun checkPassword(password: String) {
        _isErrorPassword.value = password.length >= 8
    }

    fun authorization(email: String, password: String) {
        checkEmail(email)
        checkPassword(password)
        if (_isErrorEmail.value == false || _isErrorPassword.value == false) return
        controller.login(
            LoginParams(
                email,
                password
            ),
            onSuccess = {

                _authorizationSuccess.value = it

            },
            onFailure = {
                _authorizationSuccess.value = null

            })
    }
}