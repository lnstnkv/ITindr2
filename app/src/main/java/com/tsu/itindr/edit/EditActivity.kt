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
import com.tsu.itindr.registration.model.ImagePicker
import com.tsu.itindr.request.*
import com.tsu.itindr.request.avatar.AvatarController
import com.tsu.itindr.request.profile.*
import com.tsu.itindr.request.user.UserController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class EditActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(EditViewModel::class.java) }
    private val sharedPreference by lazy { SharedPreference(this) }
    private val viewbinding by lazy { ActivityEditBinding.inflate(layoutInflater) }
    private var controller = ProfileController()
    val controllerTopic = TopicController()
    val updateController = UserController()
    val saveAvatar = AvatarController()
    val chips: MutableList<String> = mutableListOf()
    var chooseChips: List<TopicResponse> = listOf()
    private val imagePicker = ImagePicker(activityResultRegistry, this) { imageUri ->
        viewbinding.imageViewEdit.load(imageUri)
        saveAvatarFunc(imageUri)
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
       // getProfile()
        //getTopic()

        viewbinding.buttonSavEdit.setOnClickListener {
            updateProfile()
        }

    }

    private fun initView() = with(viewbinding) {
        viewModel.isErrorAvatar.observe(this@EditActivity) { isErrorAvatar ->
            if (isErrorAvatar == false) {
                Toast.makeText(this@EditActivity, R.string.error_email, Toast.LENGTH_LONG)
                    .show()
            }

        }
        viewModel.isErrorTopic.observe(this@EditActivity) {
            if (it != null)
            {
                for (i in it) {
                    addChip(i)
                }
            }
        }
        viewModel.isErrorProfile.observe(this@EditActivity){
            if(it!=null)
            {
                chooseChips = it.topics
                for (j in it.topics) {
                    chooseChip(j.title)
                }

                viewbinding.textViewAboutEdit.text = it.aboutMyself
                viewbinding.editTextEditName.setText(it.name)
                if (it.avatar != null) {
                    Glide
                        .with(imageViewEdit.context)
                        .load(it.avatar)
                        .into(viewbinding.imageViewEdit)
                }
            }
        }
    }

    private fun getProfile() {
        val sharedPreference = SharedPreference(this)
        val accessToken = sharedPreference.getValueString("accessToken")
        controller.profile(
            "Bearer " + accessToken,
            onSuccess = {
                chooseChips = it.topics
                for (j in it.topics) {
                    chooseChip(j.title)
                }

                viewbinding.textViewAboutEdit.text = it.aboutMyself
                viewbinding.editTextEditName.setText(it.name)
                if (it.avatar != null) {
                    Glide
                        .with(this)
                        .load(it.avatar)
                        .into(viewbinding.imageViewEdit)
                }

            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            }
        )
    }

    /*private fun deleteAvatar() {
        val sharedPreference = SharedPreference(this)
        val accessToken = sharedPreference.getValueString("accessToken")
          saveAvatar.deleteAvatar("Bearer " + accessToken,
            onSuccess = {
                viewbinding.imageViewEdit.setImageResource(R.drawable.ic_user)
                viewbinding.buttonChooseEdit.setText(R.string.choose_photo)
            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            })

    }
     */

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

    private fun saveAvatarFunc(uri: Uri) {
        val sharedPreference = SharedPreference(this)
        val accessToken = sharedPreference.getValueString("accessToken")
        val stream = uri.let { contentResolver.openInputStream(it) }
        val bitmap = BitmapFactory.decodeStream(stream)
        val image = imageToByteArray(bitmap)
        val requestFile: RequestBody =
            image.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)

        saveAvatar.updateAvatar(
            "Bearer " + accessToken,
            body,
            onSuccess = {

                viewbinding.buttonChooseEdit.setText(R.string.delete_photo)
                viewbinding.buttonChooseEdit.setOnClickListener { viewModel.deleteAvatar() }


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

    private fun getTopic() {

        val sharedPreference = SharedPreference(this)
        val accessToken = sharedPreference.getValueString("accessToken")
        controllerTopic.topic(
            "Bearer " + accessToken,
            onSuccess = {

                for (i in it) {
                    addChip(i)
                }
            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            }

        )
    }

    private fun updateProfile() {
        val sharedPreference = SharedPreference(this)
        val accessToken = sharedPreference.getValueString("accessToken")
        updateController.update(
            "Bearer " + accessToken,
            UpdateParams(
                viewbinding.editTextEditName.text.toString(),
                viewbinding.TextInputEdit.text.toString(),
                chips.toList()
            ),
            onSuccess = {
                Toast.makeText(this, R.string.item_save, Toast.LENGTH_LONG).show()
            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            }
        )
    }
}

