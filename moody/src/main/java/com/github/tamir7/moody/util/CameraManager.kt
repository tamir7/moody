package com.github.tamir7.moody.util

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraManager @Inject constructor() {
    fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

     fun createImageCaptureIntent(context: Context, file: File): Intent {
         val uri = FileProvider.getUriForFile(context,
             "com.github.tamir7.moody.fileprovider",
             file)
         val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
         return intent
    }
}