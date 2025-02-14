package com.tsu.itindr.tellabout

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityTellAboutBinding
import com.google.android.material.chip.Chip
import com.tsu.itindr.R
import com.google.android.material.chip.ChipGroup
import com.tsu.itindr.find.FindActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

import coil.load
import com.tsu.itindr.registration.*
import com.tsu.itindr.edit.ImagePicker
import com.tsu.itindr.data.*
import com.tsu.itindr.data.avatar.AvatarController


class TellAboutActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityTellAboutBinding
    private lateinit var imageView: ImageView
    private lateinit var chipGroup: ChipGroup
    private lateinit var bitmap: Bitmap
    private val imagePicker = ImagePicker(activityResultRegistry, this) { imageUri ->
        viewbinding.imageViewAvatar.load(imageUri)
        viewModel.saveAvatar(imageUri)
    }
    private val viewModel by lazy { ViewModelProvider(this).get(TellAboutViewModel::class.java) }

    val chips: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityTellAboutBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)

        chipGroup = viewbinding.chipGroup
        viewbinding.imageViewAvatar.clipToOutline = true
        imageView = viewbinding.imageViewAvatar

        viewbinding.button.setOnClickListener {
            imagePicker.pickImage()

        }
        initView()
        viewModel.addTopic()

        viewbinding.buttonSaveYourself.setOnClickListener {
            viewModel.updateProfile(
                viewbinding.editTextName.text.toString(),
                viewbinding.editTextAboutYourself
                    .text.toString(),
                chips.toList()
            )

        }

    }

    private fun initView() = with(viewbinding) {
        viewModel.isErrorFromTopic.observe(this@TellAboutActivity) {
            if (it == true) {
                Toast.makeText(this@TellAboutActivity, "Проблемка топиков", Toast.LENGTH_LONG)
                    .show()
            }
        }

        viewModel.topics.observe(this@TellAboutActivity) {
            viewbinding.chipGroup.removeAllViews()
            for (i in it) {
                addChip(i)
            }
        }

        viewModel.isErrorUpdateProfile.observe(this@TellAboutActivity) {
            if (it == true) {
                Toast.makeText(this@TellAboutActivity, "ОШибка updae Profile", Toast.LENGTH_LONG)
                    .show()
            } else {
                val intent = Intent(this@TellAboutActivity, FindActivity::class.java)
                startActivity(intent)
            }
        }
        viewModel.isErrorSaveAvatar.observe(this@TellAboutActivity) { isErrorSaveAvatar ->
            if (isErrorSaveAvatar == true) {
                Toast.makeText(
                    this@TellAboutActivity,
                    "Не удалось загрузить фото",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewbinding.button.setText(R.string.delete_photo)
                viewbinding.button.setOnClickListener { viewModel.deleteAvatar() }
            }

        }
        viewModel.isErrorAvatar.observe(this@TellAboutActivity) { isErrorAvatar ->
            if (isErrorAvatar == false) {
                Toast.makeText(this@TellAboutActivity, "Не удалось удалить фото", Toast.LENGTH_LONG)
                    .show()
            } else {
                viewbinding.button.setText(R.string.choose_photo)
            }

        }
    }

    private fun addChip(it: TopicItem) {
        val text = it.title
        val id = it.id
        val chip = LayoutInflater.from(this).inflate(R.layout.item_chip, null) as Chip
        chip.text = text
        chipGroup.addView(chip)
        chip.setOnClickListener {
            if (chip.isChecked) {
                chips.add(id)
            } else {
                chips.remove(id)
            }
        }

    }


}
