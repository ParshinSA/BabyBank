package com.example.babybank.data.common.utils

import java.io.File
import javax.inject.Inject

class FileNameHandler @Inject constructor() {

    fun checkNameFile(nameFile: String, folder: File?): String {
        var newNameFile = nameFile
        if (folder == null || !folder.isDirectory) return newNameFile

        var counter = 0
        val leftPath = newNameFile.substring(0, newNameFile.lastIndexOf('.') - 1)
        val rightPath =
            newNameFile.substring(newNameFile.indexOfLast { it == '.' }, newNameFile.length)

        while (File(folder.absolutePath + '/' + newNameFile).exists()) {
            val mid = "(${++counter})"
            newNameFile = leftPath + mid + rightPath
        }

        return newNameFile
    }

    fun urlToName(url: String): String {
        return url.substring(url.lastIndexOf('/') + 1, url.length)
    }
}