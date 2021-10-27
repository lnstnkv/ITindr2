package com.tsu.itindr.tellabout

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.view.get
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityRegistrationBinding
import com.tsu.itindr.databinding.ActivityTellAboutBinding
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.registration.RegistrationActivity
import com.google.android.material.chip.Chip

import com.google.android.material.chip.ChipGroup




class TellAboutActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityTellAboutBinding
    private lateinit var imageView: ImageView
    private lateinit var chipGroup: ChipGroup
    val controller = TopicController()
    private val REQUEST_TAKE_PHOTO = 1
    private val updateController = UserController()
    private fun addChip(text: String) {

        val chip = Chip(this)
        chip.setChipBackgroundColorResource(R.color.white)
        chip.setChipStrokeColorResource(R.color.pink)
        chip.setChipStrokeColorResource(R.color.pink)
        chip.text = text
        chip.setOnClickListener {
            chip.setChipBackgroundColorResource(R.color.pink)
            chip.setTextColor(Color.WHITE)
        }
        chipGroup.addView(chip)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityTellAboutBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        val sharedPreference = SharedPreference(this)

        chipGroup = viewbinding.chipGroup
        imageView = viewbinding.imageViewAvatar
        viewbinding.buttonSaveYourself.setOnClickListener {
            /* updateController.update(
            "Bearer "+ sharedPreference.getValueString("accessToken"),
            ProfileParams(
                viewbinding.editTextName.text.toString(),
                viewbinding.editTextAboutYourself.text.toString(),


            ),
            onSuccess ={
                val intent = Intent(this@TellAboutActivity, FindActivity::class.java)
                startActivity(intent)
            },
            onFailure = {
                Toast.makeText(this, "Ошибка , продолжаем тупить", Toast.LENGTH_LONG).show()
            }
        )
        }

            */
            controller.topic(
                "Bearer " + sharedPreference.getValueString("accessToken"),
                onSuccess = {
                    for (i in it) {
                        addChip(i.title)
                    }


                },
                onFailure = {
                    Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show()
                }

            )

        }
    }
}

