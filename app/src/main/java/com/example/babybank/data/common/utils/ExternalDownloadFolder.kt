package com.example.babybank.data.common.utils

import android.content.Context
import android.os.Environment
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExternalDownloadFolder @Inject constructor(
    private val context: Context
) {

    fun getExternalFolder(directory: String): File? {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return null
        return context.getExternalFilesDir(directory)
    }

    fun getFileExternalFolder(directory: String): Array<out File>? {
        return getExternalFolder(directory)?.listFiles()
    }

    companion object {
        const val FOLDER_BANK_LIST = "Bank list"
    }
}