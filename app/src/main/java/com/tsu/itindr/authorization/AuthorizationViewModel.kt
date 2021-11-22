package com.tsu.itindr.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.itindr.authorization.model.LoginController
import com.tsu.itindr.authorization.model.LoginParams
import com.tsu.itindr.authorization.model.LoginResponse

class AuthorizationViewModel:ViewModel() {

    private val controller = LoginController()

    private val _isErrorEmail=MutableLiveData<Boolean>()
    val isErrorEmail:LiveData<Boolean>
    get()=_isErrorEmail


    private val _isErrorPassword=MutableLiveData<Boolean>()
    val isErrorPassword:LiveData<Boolean>
        get()=_isErrorPassword

    private val _authorizationSuccess=MutableLiveData<LoginResponse?>()
    val authorizationSuccess:LiveData<LoginResponse?>
        get()=_authorizationSuccess


    private fun checkEmail(email: String) {
        var regex = Regex("""[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}""")
        _isErrorEmail.value = regex.matches(email)
    }
    private fun checkPassword(password: String) {
        _isErrorPassword.value = password.length >= 8
    }
    fun authorization(email: String,password: String){
        checkEmail(email)
        checkPassword(password)
        if(_isErrorEmail.value==false || _isErrorPassword.value==false) return
        controller.login(
            LoginParams(
                email,
                password
            ),
            onSuccess = {

                _authorizationSuccess.value=it

            },
            onFailure = {
                _authorizationSuccess.value=null

            })
    }
}