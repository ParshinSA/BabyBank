package com.example.babybank.data.common.utils

import java.io.File
import javax.inject.Inject

class FileNameHandler @Inject constructor() {

    // проверить, есть ли файл с полученным именем в указанной дерикториии
    fun checkNameFile(nameFile: String, folder: File?): String {
        var newNameFile = nameFile
        if (folder == null || !folder.isDirectory) return newNameFile

        var counter = 0
        val leftPath = newNameFile.substring(0, newNameFile.lastIndexOf('.') - 1)
        val rightPath =
            newNameFile.substring(newNameFile.indexOfLast { it == '.' }, newNameFile.length)

        // еслм файл с таким именем @nameFile уже существует, то генерируем новое и проверяем заного
        while (File(folder.absolutePath + '/' + newNameFile).exists()) {
            val mid = "(${++counter})"
            newNameFile = leftPath + mid + rightPath
        }

        return newNameFile
    }

    // получаем имя файла из полученного URI
    fun urlToName(uri: String): String {
        return uri.substring(uri.lastIndexOf('/') + 1, uri.length)
    }
}