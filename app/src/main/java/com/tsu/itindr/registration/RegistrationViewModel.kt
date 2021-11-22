package com.tsu.itindr.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.itindr.registration.model.RegisterController
import com.tsu.itindr.registration.model.RegisterParams
import com.tsu.itindr.registration.model.RegisterResponse

class RegistrationViewModel : ViewModel() {

    private val controller = RegisterController()
    private val _isErrorEmail = MutableLiveData<Boolean>()
    val isErrorEmail: LiveData<Boolean>
        get() = _isErrorEmail

    private val _isErrorSamePassword = MutableLiveData<Boolean>()
    val isErrorSamePassword: LiveData<Boolean>
        get() = _isErrorSamePassword
    private val _isErrorPassword = MutableLiveData<Boolean>()
    val isErrorPassword: LiveData<Boolean>
        get() = _isErrorPassword

    private val _registerSuccess = MutableLiveData<RegisterResponse?>()
    val registerSuccess: LiveData<RegisterResponse?>
        get() = _registerSuccess


    private fun checkEmail(email: String) {
        var regex = Regex("""[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}""")
        _isErrorEmail.value = regex.matches(email)
    }

    /*private fun checkPassword(password: String,passwordRepeat: String) {
        _isErrorPassword.value = password.length >= 8
    }
    */
    private fun samePassword(password: String, passwordRepeat: String) {
        _isErrorSamePassword.value = passwordRepeat.contentEquals(password)
    }

    fun register(email: String, password: String,passwordRepeat: String) {
        checkEmail(email)
        samePassword(password,passwordRepeat)
        if(_isErrorEmail.value==false || _isErrorSamePassword.value==false) return
        controller.register(
            RegisterParams(email, password),
            onSuccess = {
                _registerSuccess.value = it

            },
            onFailure = {
                _registerSuccess.value = null
            })
    }
}