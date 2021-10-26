package com.tsu.itindr.tellabout

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.tsu.itindr.ProfileController
import com.tsu.itindr.R
import com.tsu.itindr.SharedPreference
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.databinding.ActivityTellAboutBinding
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.registration.RegistrationActivity

class TellAboutActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityTellAboutBinding
    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var imageView: CardView
    private val controller = ProfileController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityTellAboutBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        val sharedPreference = SharedPreference(this)
        val intent = Intent(this@TellAboutActivity, FindActivity::class.java)
        viewbinding.buttonSaveYourself.setOnClickListener { startActivity(intent) }
        controller.profile(
            "Bearer:"+ sharedPreference.getValueString("accessToken"),
            onSuccess ={
                Toast.makeText(this, "Все прекрасно мы живы", Toast.LENGTH_LONG).show()
            },
            onFailure = {
                Toast.makeText(this, "Ошибка , продолжаем тупить", Toast.LENGTH_LONG).show()
            }

        )
    }
}