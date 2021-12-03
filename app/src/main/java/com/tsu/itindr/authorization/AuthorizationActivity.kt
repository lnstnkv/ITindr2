package com.tsu.itindr.authorization

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityAuthorizationBinding
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.data.SharedPreference

class AuthorizationActivity : AppCompatActivity() {

    private val viewbinding by lazy { ActivityAuthorizationBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(this).get(AuthorizationViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setContentView(viewbinding.root)
        viewbinding.buttonBackAuto.setOnClickListener { this.finish() }
        viewbinding.buttonComeAuto.setOnClickListener {

            viewModel.authorization(
                viewbinding.editTextEmailAddressAuto.text.toString(),
                viewbinding.editTextPasswordAuto.text.toString()
            )
        }
    }

    private fun initView() = with(viewbinding) {
        viewModel.isErrorEmail.observe(this@AuthorizationActivity) { isErrorEmail ->
            if (isErrorEmail == false) {
                Toast.makeText(this@AuthorizationActivity, R.string.error_email, Toast.LENGTH_LONG)
                    .show()
            }

        }
        viewModel.isErrorPassword.observe(this@AuthorizationActivity) { isErrorPassword ->
            if (isErrorPassword == false) {
                Toast.makeText(this@AuthorizationActivity, "Проверьте пароль", Toast.LENGTH_LONG)
                    .show()
            }
        }
        viewModel.authorizationSuccess.observe(this@AuthorizationActivity) {
            if (it != null) {
                val intent =
                    Intent(this@AuthorizationActivity, FindActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@AuthorizationActivity, R.string.error, Toast.LENGTH_LONG).show()
            }

        }

    }
}

