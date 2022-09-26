package com.example.babybank.presentation.models

import android.content.Context
import android.os.Environment
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExternalDownloadFolder @Inject constructor(
    private val context: Context
) {

    private val externalDownloadFolder =
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

    fun getExternalFolder(): File? {
        if (!checkStateExternalStorage()) return null
        return externalDownloadFolder
    }

    fun checkStateExternalStorage() =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun getFileExternalFolder(): Array<out File>? {
        return getExternalFolder()?.absoluteFile?.listFiles()
    }

}