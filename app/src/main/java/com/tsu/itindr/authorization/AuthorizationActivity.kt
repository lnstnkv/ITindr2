package com.tsu.itindr.authorization

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tsu.itindr.LoginController
import com.tsu.itindr.LoginParams
import com.tsu.itindr.R
import com.tsu.itindr.RegisterController
import com.tsu.itindr.databinding.ActivityAuthorizationBinding
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.tellabout.TellAboutActivity

class AuthorizationActivity : AppCompatActivity() {
    private val controller = LoginController()
    private lateinit var viewbinding: ActivityAuthorizationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.buttonBackAuto.setOnClickListener { this.finish() }
        viewbinding.buttonComeAuto.setOnClickListener {
            controller.login(
                LoginParams(
                    viewbinding.editTextEmailAddressAuto.text.toString(),
                    viewbinding.editTextPasswordAuto.text.toString()
                ),
                onSuccess = {
                    if(emailRegex(viewbinding.editTextEmailAddressAuto.text.toString())) {
                        val intent =
                            Intent(this@AuthorizationActivity, TellAboutActivity::class.java)
                        startActivity(intent)
                    }
                },
                onFailure = {
                    Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show()
                })
        }
    }
}
private fun emailRegex(email:String): Boolean{
    var regex= Regex("\\w*@\\w*\\.[a-zA-Z]*")
    return regex.matches(email)
}