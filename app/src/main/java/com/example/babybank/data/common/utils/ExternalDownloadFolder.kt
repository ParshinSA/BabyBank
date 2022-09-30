package com.example.babybank.data.common.utils

import android.content.Context
import android.os.Environment
import com.example.babybank.R
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExternalDownloadFolder @Inject constructor(
    private val context: Context
) {

    fun getExternalFolder(directory: String): File? {
        if (!checkStateExternalStorage()) return null
        return context.getExternalFilesDir(directory)
    }

    fun checkStateExternalStorage() =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun getFileExternalFolder(directory: String): Array<out File>? {
        return getExternalFolder(directory)?.listFiles()
    }
companion object{
    const val FOLDER_BANK_LIST = "Bank list"
}
}