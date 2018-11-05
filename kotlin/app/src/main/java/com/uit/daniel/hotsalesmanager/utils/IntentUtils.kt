package com.uit.daniel.hotsalesmanager.utils

import android.content.Intent
import android.provider.MediaStore

class IntentUtils {

    fun intentActionPick(): Intent {
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    fun intentActionImageCapture(): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    }
}