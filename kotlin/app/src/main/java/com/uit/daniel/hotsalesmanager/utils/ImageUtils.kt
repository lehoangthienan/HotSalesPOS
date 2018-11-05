package com.uit.daniel.hotsalesmanager.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.support.v4.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.uit.daniel.hotsalesmanager.utils.Constant.FORMAT_NAME_IMAGE
import com.uit.daniel.hotsalesmanager.utils.Constant.IMG
import com.uit.daniel.hotsalesmanager.utils.Constant.JPG
import com.uit.daniel.hotsalesmanager.utils.Constant.NAME_PROVIDER
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImageUtils {

    @Throws(IOException::class)
    fun createImageFile(activity: Activity): File {
        val timeStamp = SimpleDateFormat(
            FORMAT_NAME_IMAGE,
            Locale.getDefault()
        ).format(Date())
        val imageFileName = IMG + timeStamp + "_"
        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName,
            JPG,
            storageDir
        )
    }

    fun createPhotoURI(image: File, activity: Activity): Uri {
        return FileProvider.getUriForFile(activity, NAME_PROVIDER, image)
    }

    fun renderImage(context: Context, avatar: String, target: SimpleTarget<Bitmap>) {
        Glide.with(context)
            .asBitmap()
            .load(avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(target)

    }


}