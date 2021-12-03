package com.tsu.itindr.data.avatar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class AvatarController()
 {
    private val api: AvatarInt = Network.retrofitMultipart.create(AvatarInt::class.java)

   /* val stream = uri.let { context.contentResolver.openInputStream(uri) }
    val bitmap = BitmapFactory.decodeStream(stream)
    val image = imageToByteArray(bitmap)
    val requestFile: RequestBody =
        image.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)
*/


    fun updateAvatar(
        accessToken: String,
        avatar: MultipartBody.Part,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.upload(accessToken, avatar).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }

    fun deleteAvatar(
        accessToken: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.delete(accessToken).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onFailure.invoke()
            }

        })
    }

   private fun imageToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutput = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutput)
        return byteArrayOutput.toByteArray()
    }
}

