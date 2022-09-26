package com.example.babybank.data.common.utils

import android.app.DownloadManager
import android.content.Context
import androidx.core.content.getSystemService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDownloadManager @Inject constructor(
    context: Context
) {
    private val manager = checkNotNull(context.getSystemService<DownloadManager>())

    fun manager(): DownloadManager {
        return manager
    }
}