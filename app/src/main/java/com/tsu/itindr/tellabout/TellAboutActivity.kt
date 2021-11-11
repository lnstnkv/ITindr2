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

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import coil.load
import com.tsu.itindr.registration.*
import com.tsu.itindr.request.*
import com.tsu.itindr.request.avatar.AvatarController
import com.tsu.itindr.request.profile.TopicController
import com.tsu.itindr.request.profile.TopicResponse
import com.tsu.itindr.request.profile.UpdateParams
import com.tsu.itindr.request.user.UserController


class TellAboutActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityTellAboutBinding
    private lateinit var imageView: ImageView
    private lateinit var chipGroup: ChipGroup
    private lateinit var bitmap: Bitmap
    private val imagePicker = ImagePicker(activityResultRegistry, this) { imageUri ->
        viewbinding.imageViewAvatar.load(imageUri)
        saveAvatarFunc(imageUri)
    }
    val controller = TopicController()
    val saveAvatar = AvatarController()
    private val REQUEST_TAKE_PHOTO = 1
    private val GALLERY_REQUEST = 1
    private val updateController = UserController()
    val chips: MutableList<String> = mutableListOf()

    private fun addChip(it: TopicResponse) {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityTellAboutBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        val sharedPreference = SharedPreference(this)

        chipGroup = viewbinding.chipGroup
        viewbinding.imageViewAvatar.clipToOutline = true
        imageView = viewbinding.imageViewAvatar

        viewbinding.button.setOnClickListener {
            imagePicker.pickImage()

        }

        controller.topic(
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

        viewbinding.buttonSaveYourself.setOnClickListener {
            updateController.update(
                "Bearer " + sharedPreference.getValueString("accessToken"),
                UpdateParams(
                    viewbinding.editTextName.text.toString(),
                    viewbinding.editTextAboutYourself
                    .text.toString(),
                    chips.toList()
                ),
                onSuccess = {
                    val intent = Intent(this@TellAboutActivity, FindActivity::class.java)
                    startActivity(intent)
                },
                onFailure = {
                    Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
                }
            )
            // val intent = Intent(this@TellAboutActivity, FindActivity::class.java)
            //startActivity(intent)
        }


    }

    private fun deleteAvatar() {
        val sharedPreference = SharedPreference(this)

        saveAvatar.deleteAvatar("Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {
                viewbinding.imageViewAvatar.setImageResource(R.drawable.ic_user)
                viewbinding.button.setText(R.string.choose_photo)
            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            })

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

                viewbinding.button.setText(R.string.delete_photo)
                viewbinding.button.setOnClickListener { deleteAvatar() }


            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            })

    }

    private fun saveAvatarFuncGallery(bitmap: Bitmap) {
        val sharedPreference = SharedPreference(this)
        val image = imageToByteArray(bitmap)
        val requestFile: RequestBody =
            image.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)

        saveAvatar.updateAvatar(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            body,
            onSuccess = {

                viewbinding.button.setText(R.string.delete_photo)


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

class ImagePicker(
    private val activityResultRegistry: ActivityResultRegistry,
    private val lifecycleOwner: LifecycleOwner,
    private val callback: (imageUri: Uri) -> Unit

) {
    private val getContent: ActivityResultLauncher<String> =
        activityResultRegistry.register(
            REGISTER_KEY, lifecycleOwner,
            ActivityResultContracts.GetContent(),
            callback,

            )

    fun pickImage() {
        getContent.launch(MIMETYPE_IMAGES)
    }

    private companion object {
        private const val REGISTER_KEY = "Avatar"
    }
}
