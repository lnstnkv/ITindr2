package com.tsu.itindr.authorization

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityAuthorizationBinding
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.request.login.LoginController
import com.tsu.itindr.request.login.LoginParams
import com.tsu.itindr.request.SharedPreference

class AuthorizationActivity : AppCompatActivity() {
    private val controller = LoginController()
    private lateinit var viewbinding: ActivityAuthorizationBinding
    val sharedPreference = SharedPreference(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewbinding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.buttonBackAuto.setOnClickListener { this.finish() }
        viewbinding.buttonComeAuto.setOnClickListener {
            if (emailRegex(viewbinding.editTextEmailAddressAuto.text.toString())) {
              authorizationProfile()
            }
            else
            {
                Toast.makeText(this, R.string.error_email, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun authorizationProfile() {
        controller.login(
            LoginParams(
                viewbinding.editTextEmailAddressAuto.text.toString(),
                viewbinding.editTextPasswordAuto.text.toString()
            ),
            onSuccess = {
                sharedPreference.save("accessToken",it.accessToken)
                val intent =
                    Intent(this@AuthorizationActivity, FindActivity::class.java)
                startActivity(intent)

            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            })
    }
}

private fun emailRegex(email: String): Boolean {
    var regex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")
    return regex.matches(email)
}