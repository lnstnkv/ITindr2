package com.tsu.itindr.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.itindr.authorization.AuthorizationActivity
import com.tsu.itindr.registration.RegistrationActivity
import com.tsu.itindr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        val intent = Intent(this@MainActivity,RegistrationActivity::class.java)
        viewbinding.buttonRegister.setOnClickListener { startActivity(intent) }
        val intentAuthorization = Intent(this@MainActivity,AuthorizationActivity::class.java)
        viewbinding.buttonCome.setOnClickListener { startActivity(intentAuthorization) }
    }

}