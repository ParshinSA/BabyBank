package com.example.babybank.data.data_source.remote

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import com.example.babybank.R
import com.example.babybank.data.common.utils.AppDownloadManager
import com.example.babybank.data.common.utils.ExternalDownloadFolder
import com.example.babybank.data.common.utils.FileNameHandler
import com.example.babybank.data.data_source.interf.DownloadDataSource
import io.reactivex.Completable
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadManagerDownloadDataSourceImpl @Inject constructor(
    private val externalDownloadFolder: ExternalDownloadFolder,
    private val fileNameHandler: FileNameHandler,
    downloadManager: AppDownloadManager,
    private val context: Context,
) : DownloadDataSource {

    private val manager = downloadManager.manager()

    override fun download(url: String, directory: String): Completable {
        val downloadFolder = externalDownloadFolder.getExternalFolder(directory)
        return Completable.create { subscription ->
            if (subscription.isDisposed) return@create

            var nameFile = fileNameHandler.urlToName(url)
            nameFile = fileNameHandler.checkNameFile(nameFile, downloadFolder)

            val request = DownloadManager.Request(Uri.parse(url))
                .setDescription(nameFile)
                .setTitle("${context.getString(R.string.download)}: $nameFile")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalFilesDir(context, downloadFolder?.name, nameFile)

            val loadId = manager.enqueue(request)
            when (loadProcess(loadId)) {
                DownloadManager.STATUS_SUCCESSFUL -> subscription.onComplete()
                else -> {
                    File(downloadFolder, nameFile).delete()
                    subscription.onError(IOException("Failed loading $url"))
                }
            }
        }
    }

    private fun loadProcess(loadId: Long): Int {
        var isLoading = true
        var status = DownloadManager.STATUS_RUNNING

        while (isLoading) {

            val query = DownloadManager.Query().setFilterById(loadId)
            val cursor = manager.query(query)
            cursor.moveToFirst()

            status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))

            isLoading = when (status) {
                DownloadManager.STATUS_SUCCESSFUL -> false
                DownloadManager.STATUS_FAILED -> false
                else -> continue
            }
            cursor.close()
        }
        return status
    }
}
