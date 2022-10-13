package com.example.babybank.data.common.utils

import android.app.DownloadManager
import android.content.Context
import androidx.core.content.getSystemService
import com.example.babybank.common.di.scope.AppScope
import javax.inject.Inject

@AppScope
class AppDownloadManager @Inject constructor(
    context: Context
) {
    private val manager = checkNotNull(context.getSystemService<DownloadManager>())

    fun manager(): DownloadManager {
        return manager
    }
}