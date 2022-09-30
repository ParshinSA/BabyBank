package com.example.babybank.presentation.common

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.babybank.BuildConfig
import java.io.File
import javax.inject.Inject

class FileUriProvider @Inject constructor(
    private val context: Context
) {

    private val authority = "${BuildConfig.APPLICATION_ID}.file_provider"

    fun getUri(file: File): Uri? {
        return FileProvider.getUriForFile(context, authority, file)
    }
}