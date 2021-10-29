package com.tsu.itindr.edit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import coil.load
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityEditBinding
import com.tsu.itindr.request.*
import com.tsu.itindr.request.avatar.AvatarController
import com.tsu.itindr.request.profile.*
import com.tsu.itindr.tellabout.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class EditActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityEditBinding
    private var controller = ProfileController()
    val controllerTopic = TopicController()
    val updateController = UserController()
    val saveAvatar = AvatarController()
    val chips: MutableList<String> = mutableListOf()
    val chooseChip: MutableList<String> = mutableListOf()
    private val imagePicker = ImagePicker(activityResultRegistry, this) { imageUri ->
        viewbinding.imageViewEdit.load(imageUri)
        saveAvatarFunc(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = SharedPreference(this)
        viewbinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.imageViewEdit.clipToOutline = true
        viewbinding.materialToolbar.setOnClickListener { finish() }
        viewbinding.buttonChooseEdit.setOnClickListener {
            imagePicker.pickImage()

        }
        controller.profile(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {
                for (j in it.topics) {
                    chooseChip(j.title)
                }
                viewbinding.textViewAboutEdit.text = it.aboutMyself
                viewbinding.editTextEditName.setText(it.name)
                Glide
                    .with(this)
                    .load(it.avatar)
                    .into(viewbinding.imageViewEdit);

            },
            onFailure = {

            }
        )
        controllerTopic.topic(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {

                for (i in it) {
                    addChip(i)
                }


            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            }

        )
        viewbinding.buttonSavEdit.setOnClickListener {
            updateController.update(
                "Bearer " + sharedPreference.getValueString("accessToken"),
                UpdateParams(
                    viewbinding.editTextEditName.text.toString(),
                    viewbinding.textViewAboutEdit.text.toString(),
                    chips.toList()
                ),
                onSuccess = {
                    Toast.makeText(this, R.string.item_save, Toast.LENGTH_LONG).show()
                },
                onFailure = {

                }
            )

        }


    }

    private fun deleteAvatar() {
        val sharedPreference = SharedPreference(this)

        saveAvatar.deleteAvatar("Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {
                viewbinding.imageViewEdit.setImageResource(R.drawable.ic_user)
                viewbinding.buttonChooseEdit.setText(R.string.choose_photo)
            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            })

    }

    private fun addChip(it: TopicResponse) {
        val text = it.title
        val id = it.id
        val chipWhite = LayoutInflater.from(this).inflate(R.layout.item_chip, null) as Chip
        chipWhite.text = text
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

    private fun saveAvatarFunc(uri: Uri) {
        val sharedPreference = SharedPreference(this)
        val stream = uri.let { contentResolver.openInputStream(it) }
        val bitmap = BitmapFactory.decodeStream(stream)
        val image = imageToByteArray(bitmap)
        val requestFile: RequestBody =
            image.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)

        saveAvatar.updateAvatar(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            body,
            onSuccess = {

                viewbinding.buttonChooseEdit.setText(R.string.delete_photo)
                viewbinding.buttonChooseEdit.setOnClickListener { deleteAvatar() }


            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            })

    }

    fun imageToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutput = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutput)
        return byteArrayOutput.toByteArray()
    }
}

