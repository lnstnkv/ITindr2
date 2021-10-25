package com.tsu.itindr.tellabout

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.tsu.itindr.ProfileController
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.databinding.ActivityTellAboutBinding
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.registration.RegistrationActivity

class TellAboutActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityTellAboutBinding
    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var imageView: ImageView
    private val controller2 = ProfileController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityTellAboutBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        val intent = Intent(this@TellAboutActivity, FindActivity::class.java)
        viewbinding.buttonSaveYourself.setOnClickListener { startActivity(intent) }















        imageView = viewbinding.imageViewAvatar
        viewbinding.textViewChoosePhoto.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK && data !== null) {
                    imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            else -> {
                Toast.makeText(this, "Wrong request code", Toast.LENGTH_SHORT).show()
            }
        }
    }
}