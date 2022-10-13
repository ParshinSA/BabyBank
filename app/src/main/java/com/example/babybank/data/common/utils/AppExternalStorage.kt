package com.example.babybank.data.common.utils

import android.content.Context
import android.os.Environment
import io.reactivex.Single
import java.io.File
import java.io.IOException
import javax.inject.Inject

class AppExternalStorage @Inject constructor(
    private val context: Context
) {

    fun getExternalFolder(directory: String): File? {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return null
        return context.getExternalFilesDir(directory)
    }

    fun getListOfFilesFromExternalFolder(directory: String): Single<List<File>> {
        return Single.create { emitter ->
            val externalFolder = getExternalFolder(directory)
            val fileList = externalFolder?.listFiles()?.toList()

            if (fileList != null)
                emitter.onSuccess(fileList)
            else
                emitter.onError(IOException("Error getting list of files from directory $directory"))
        }
    }

    fun getFileFromExternalFolder(fileName: String, directory: String): Single<File> {
        return getListOfFilesFromExternalFolder(directory)
            .map { fileList ->
                fileList.first { file: File ->
                    file.name == fileName
                }
            }
    }

    companion object {
        const val FOLDER_BANK_LIST = "Bank list"
    }
}