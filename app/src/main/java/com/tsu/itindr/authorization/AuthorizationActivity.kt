package com.tsu.itindr.authorization

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityAuthorizationBinding
import com.tsu.itindr.find.FindActivity

class AuthorizationActivity : AppCompatActivity() {
    private val controller = LoginController()
    private lateinit var viewbinding: ActivityAuthorizationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = SharedPreference(this)
        viewbinding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.buttonBackAuto.setOnClickListener { this.finish() }
        viewbinding.buttonComeAuto.setOnClickListener {
            if (emailRegex(viewbinding.editTextEmailAddressAuto.text.toString())) {
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
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show()
                    })
            }
        }
    }
}

private fun emailRegex(email: String): Boolean {
    var regex = Regex("\\w*@\\w*\\.[a-zA-Z]*")
    return regex.matches(email)
}