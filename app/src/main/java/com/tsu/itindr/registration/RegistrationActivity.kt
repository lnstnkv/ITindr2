package com.tsu.itindr.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tsu.itindr.R
import com.tsu.itindr.RegisterController
import com.tsu.itindr.RegisterParams
import com.tsu.itindr.databinding.ActivityMainBinding
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.tellabout.TellAboutActivity

class RegistrationActivity : AppCompatActivity() {
    private val conroller = RegisterController()
    private lateinit var viewbinding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.buttonComeRegister.setOnClickListener {
            conroller.register(
                RegisterParams(
                    viewbinding.editTextEmailAddressReg.text.toString(),
                    viewbinding.editTextTextPassworReg.text.toString()
                ),
                onSuccess = {
                    val intent = Intent(this@RegistrationActivity, TellAboutActivity::class.java)
                    viewbinding.buttonComeRegister.setOnClickListener { startActivity(intent) }

                },
                onFailure = {
                    Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show()
                })
        }
    }
}