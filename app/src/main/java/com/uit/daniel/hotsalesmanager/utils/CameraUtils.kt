package com.uit.daniel.hotsalesmanager.utils

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

class CameraUtils {
    fun putUriImage(takePictureIntent: Intent, uriImage: Uri) {
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage)
    }
}