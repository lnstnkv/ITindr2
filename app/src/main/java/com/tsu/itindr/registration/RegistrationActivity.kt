package com.tsu.itindr.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.request.register.RegisterController
import com.tsu.itindr.request.register.RegisterParams
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.tellabout.TellAboutActivity


class RegistrationActivity : AppCompatActivity() {
    private val controller = RegisterController()

    private lateinit var token: String
    private lateinit var viewbinding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = SharedPreference(this)
        viewbinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.buttonBackRegister.setOnClickListener { this.finish() }
        viewbinding.buttonComeRegister.setOnClickListener {
            if (samePassword(
                    viewbinding.editTextTextPassworReg.text.toString(),
                    viewbinding.editTextTextPasswordTwice.text.toString()
                )
            ) {
                if ((emailRegex(viewbinding.editTextEmailAddressReg.text.toString()))) {
                    controller.register(
                        RegisterParams(
                            viewbinding.editTextEmailAddressReg.text.toString(),
                            viewbinding.editTextTextPassworReg.text.toString()
                        ),
                        onSuccess = {

                            sharedPreference.save("accessToken", it.accessToken)
                            val intent =
                                Intent(this@RegistrationActivity, TellAboutActivity::class.java)
                            startActivity(intent)

                        },
                        onFailure = {
                            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
                        })
                } else {
                    Toast.makeText(this, R.string.error_email, Toast.LENGTH_LONG).show()
                }
            } else
            {
                Toast.makeText(this, R.string.error_pwd, Toast.LENGTH_LONG).show()
            }
        }

    }
}

private fun emailRegex(email: String): Boolean {
    val regex =
        Regex("""[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}""") //[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}
    return regex.matches(email)
}

private fun samePassword(password: String, passwordRepeat: String): Boolean {
    return passwordRepeat.contentEquals(password)
}