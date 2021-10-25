package com.tsu.itindr.registration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tsu.itindr.ProfileController
import com.tsu.itindr.RegisterController
import com.tsu.itindr.RegisterParams
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.tellabout.TellAboutActivity


class RegistrationActivity : AppCompatActivity() {
    private val controller = RegisterController()
    private val controller2 = ProfileController()
    private lateinit var accessToken: String
    private lateinit var viewbinding: ActivityRegistrationBinding
    val APP_PREFERENCES = "token"
    var mSettings: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        viewbinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.buttonBackRegister.setOnClickListener { this.finish() }
        viewbinding.buttonComeRegister.setOnClickListener {
            if (samePassword(
                    viewbinding.editTextTextPassworReg.text.toString(),
                    viewbinding.editTextTextPasswordTwice.text.toString()
                ) && (emailRegex(viewbinding.editTextEmailAddressReg.text.toString()))
            ) {
                /*controller2.profile(
                "Bearer "+ accessToken,
            )
            */
                controller.register(
                    RegisterParams(
                        viewbinding.editTextEmailAddressReg.text.toString(),
                        viewbinding.editTextTextPassworReg.text.toString()
                    ),
                    onSuccess = {
                        // accessToken=
                        val intent =
                            Intent(this@RegistrationActivity, TellAboutActivity::class.java)
                        startActivity(intent)

                    },
                    onFailure = {
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show()
                    })
            }
        }

    }
}
private fun emailRegex(email:String): Boolean{
    var regex= Regex("\\w*@\\w*\\.[a-zA-Z]*")
    return regex.matches(email)
}
private fun samePassword(password: String, passwordRepeat: String): Boolean {
    return passwordRepeat.contentEquals(password)
}