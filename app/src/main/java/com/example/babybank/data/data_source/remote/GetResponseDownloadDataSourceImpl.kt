package com.example.babybank.data.data_source.remote

import com.example.babybank.common.di.scope.AppScope
import com.example.babybank.data.common.utils.AppExternalStorage
import com.example.babybank.data.common.utils.FileNameHandler
import com.example.babybank.data.data_source.interf.DownloadDataSource
import com.example.babybank.data.data_source.interf.GoogleComDataSource
import io.reactivex.Completable
import java.io.File
import javax.inject.Inject

@AppScope
class GetResponseDownloadDataSourceImpl @Inject constructor(
    private val appExternalStorage: AppExternalStorage,
    private val googleComDataSource: GoogleComDataSource,
    private val fileNameHandler: FileNameHandler,
) : DownloadDataSource {


    override fun download(url: String, directory: String): Completable {
        val downloadFolder = appExternalStorage.getExternalFolder(directory)
        return Completable.create { subscription ->
            if (subscription.isDisposed) return@create

            var nameFile = fileNameHandler.urlToName(url)
            nameFile = fileNameHandler.checkNameFile(nameFile, downloadFolder)
            val file = File(downloadFolder, nameFile)

            val pdfResponse = googleComDataSource.download(url)?.byteStream()

            try {
                file.outputStream().use { fileOutputStream ->
                    pdfResponse.use { inputStream ->
                        inputStream?.copyTo(fileOutputStream)
                    }
                }

                subscription.onComplete()
            } catch (t: Throwable) {
                file.delete()
                subscription.onError(t)
            }
        }
    }
}