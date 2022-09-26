package com.example.babybank.data.data_source.remote

import com.example.babybank.data.common.utils.FileNameHandler
import com.example.babybank.data.data_source.interf.DownloadDataSource
import com.example.babybank.data.data_source.interf.GoogleComDataSource
import com.example.babybank.presentation.models.ExternalDownloadFolder
import io.reactivex.Completable
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetResponseDownloadDataSourceImpl @Inject constructor(
    private val externalDownloadFolder: ExternalDownloadFolder,
    private val googleComDataSource: GoogleComDataSource,
    private val fileNameHandler: FileNameHandler,
) : DownloadDataSource {

    private val downloadFolder get() = externalDownloadFolder.getExternalFolder()

    override fun download(url: String): Completable {
        return Completable.create { subscription ->

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
                subscription.onError(t)
            }
        }
    }
}