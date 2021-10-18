package com.tsu.itindr.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tsu.itindr.RegisterController
import com.tsu.itindr.RegisterParams
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.tellabout.TellAboutActivity

class RegistrationActivity : AppCompatActivity() {
    private val controller = RegisterController()
    private lateinit var viewbinding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.buttonBackRegister.setOnClickListener { this.finish() }
        viewbinding.buttonComeRegister.setOnClickListener {
            controller.register(
                RegisterParams(
                    viewbinding.editTextEmailAddressReg.text.toString(),
                    viewbinding.editTextTextPassworReg.text.toString()
                ),
                onSuccess = {
                    if (samePassword(
                            viewbinding.editTextTextPassworReg.text.toString(),
                            viewbinding.editTextTextPasswordTwice.text.toString()
                        )&&(emailRegex( viewbinding.editTextEmailAddressReg.text.toString()))
                    ) {
                        val intent =
                            Intent(this@RegistrationActivity, TellAboutActivity::class.java)
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
private fun samePassword(password: String, passwordRepeat: String): Boolean {
    return passwordRepeat.contentEquals(password)
}