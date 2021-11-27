package com.tsu.itindr.edit.model


import android.graphics.Bitmap
import android.graphics.BitmapFactory

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import android.net.Uri
import android.widget.Toast
import com.tsu.itindr.R
import com.tsu.itindr.request.SharedPreference
import android.content.ContentResolver as ContentResolver

class Avatar {

    fun imageToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutput = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutput)
        return byteArrayOutput.toByteArray()
    }

   /* private fun saveAvatarFunc(uri: Uri) {
        val stream = uri.let { contentResolver.openInputStream(it) }
        val bitmap = BitmapFactory.decodeStream(stream)
        val image = imageToByteArray(bitmap)
        val requestFile: RequestBody =
            image.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)

    }

    */
}

