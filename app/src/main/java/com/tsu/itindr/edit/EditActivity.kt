package com.tsu.itindr.edit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.tsu.itindr.*

import com.tsu.itindr.databinding.ActivityEditBinding
import com.tsu.itindr.data.*
import com.tsu.itindr.data.avatar.AvatarController
import com.tsu.itindr.data.profile.*
import com.tsu.itindr.data.user.UserController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class EditActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(EditViewModel::class.java) }

    private val viewbinding by lazy { ActivityEditBinding.inflate(layoutInflater) }

    val chips: MutableList<String> = mutableListOf()
    var chooseChips: List<TopicResponse> = listOf()

    private val imagePicker = ImagePicker(activityResultRegistry, this) { imageUri ->
        viewbinding.imageViewEdit.load(imageUri)
        viewModel.saveAvatar(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setContentView(viewbinding.root)

        viewbinding.imageViewEdit.clipToOutline = true
        viewbinding.materialToolbar.setOnClickListener { finish() }

        viewbinding.buttonChooseEdit.setOnClickListener {
            imagePicker.pickImage()

        }
        viewModel.addTopic()
        viewModel.getProfile()


        viewbinding.buttonSavEdit.setOnClickListener {
            viewModel.updateProfile(
                viewbinding.editTextEditName.text.toString(),
                viewbinding.TextInputEdit.text.toString(),
                chips.toList()
            )
        }

    }

    private fun initView() = with(viewbinding) {
        viewModel.isErrorSaveAvatar.observe(this@EditActivity){ isErrorSaveAvatar->
            if(isErrorSaveAvatar==true){
                Toast.makeText(this@EditActivity, "Не удалось загрузить фото", Toast.LENGTH_LONG).show()
            }
            else{
                viewbinding.buttonChooseEdit.setText(R.string.delete_photo)
                viewbinding.buttonChooseEdit.setOnClickListener { viewModel.deleteAvatar() }
            }

        }
        viewModel.isErrorAvatar.observe(this@EditActivity) { isErrorAvatar ->
            if (isErrorAvatar == false) {
                Toast.makeText(this@EditActivity, "Не удалось удалить фото", Toast.LENGTH_LONG)
                    .show()
            }
            else{
                viewbinding.buttonChooseEdit.setText(R.string.choose_photo)
            }

        }
        viewModel.isErrorFromTopic.observe(this@EditActivity){
            if(it==true)
            {
                Toast.makeText(this@EditActivity, "Проблема отображения топиков", Toast.LENGTH_LONG)
                    .show()
            }
        }
        viewModel.isTopic.observe(this@EditActivity) {
            if (it != null) {
                for (i in it) {
                    addChip(i)
                }
            }
        }
        viewModel.isErrorProfile.observe(this@EditActivity) { profileItem->
            if (profileItem != null) {
                chooseChips = profileItem.topics
                for (j in profileItem.topics) {
                    chooseChip(j.title)
                }

                viewbinding.textViewAboutEdit.text = profileItem.aboutMyself
                viewbinding.editTextEditName.setText(profileItem.name)
                if (profileItem.avatar != null) {
                    Glide
                        .with(imageViewEdit.context)
                        .load(profileItem.avatar)
                        .into(viewbinding.imageViewEdit)
                }
            }
        }
        viewModel.isErrorUpdateProfile.observe(this@EditActivity) {
            if (it == null) {
                Toast.makeText(this@EditActivity, "Ошибка обновлнеия профиля", Toast.LENGTH_LONG)
                    .show()
            }
            else{
                Toast.makeText(this@EditActivity, "Обновление профиля просто успешно", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun addChip(it: TopicResponse) {
        val text = it.title
        val id = it.id
        val chipWhite = LayoutInflater.from(this).inflate(R.layout.item_chip, null) as Chip
        chipWhite.text = text
        for (i in chooseChips) {
            if (i.title == text) {
                chipWhite.isChecked
            }
        }
        viewbinding.chipGroup.addView(chipWhite)
        chipWhite.setOnClickListener {
            if (chipWhite.isChecked) {
                chips.add(id)
            } else {
                chips.remove(id)
            }
        }
    }

    private fun chooseChip(text: String) {

        val chip = LayoutInflater.from(this).inflate(R.layout.item_chip_pink, null) as Chip
        chip.text = text
        viewbinding.chipGroup.addView(chip)
    }
}

