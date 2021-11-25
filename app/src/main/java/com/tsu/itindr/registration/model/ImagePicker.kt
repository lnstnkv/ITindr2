package com.tsu.itindr.registration.model

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import com.tsu.itindr.MIMETYPE_IMAGES

class ImagePicker(
    private val activityResultRegistry: ActivityResultRegistry,
    private val lifecycleOwner: LifecycleOwner,
    private val callback: (imageUri: Uri) -> Unit

) {
    val getContent: ActivityResultLauncher<String> =
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
