package com.tsu.itindr.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.tellabout.TellAboutActivity


class RegistrationActivity : AppCompatActivity() {

    private val viewbinding by lazy { ActivityRegistrationBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }
    private val sharedPreference by lazy { SharedPreference(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setContentView(viewbinding.root)
        viewbinding.buttonBackRegister.setOnClickListener { this.finish() }
        viewbinding.buttonComeRegister.setOnClickListener {
            viewModel.register( viewbinding.editTextEmailAddressReg.text.toString(),
                viewbinding.editTextTextPassworReg.text.toString(), viewbinding.editTextTextPasswordTwice.text.toString())

        }
    }
    private fun initView() = with(viewbinding) {
        viewModel.isErrorEmail.observe(this@RegistrationActivity) { isErrorEmail ->
            if (isErrorEmail == false) {
                Toast.makeText(this@RegistrationActivity, R.string.error_email, Toast.LENGTH_LONG)
                    .show()
            }

        }
        viewModel.isErrorSamePassword.observe(this@RegistrationActivity) { isErrorPassword ->
            if (isErrorPassword == false) {
                Toast.makeText(this@RegistrationActivity, R.string.error_pwd, Toast.LENGTH_LONG)
                    .show()
            }
        }
        viewModel.registerSuccess.observe(this@RegistrationActivity) {
            if (it != null) {
                val intent =
                    Intent(this@RegistrationActivity, TellAboutActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@RegistrationActivity, R.string.error, Toast.LENGTH_LONG).show()
            }

        }

    }
}
