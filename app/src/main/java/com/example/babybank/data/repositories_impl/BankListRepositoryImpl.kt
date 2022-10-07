package com.example.babybank.data.repositories_impl

import com.example.babybank.data.data_source.interf.MockDataSource
import com.example.babybank.data.data_source.remote.DownloadManagerDownloadDataSourceImpl
import com.example.babybank.data.data_source.remote.GetResponseDownloadDataSourceImpl
import com.example.babybank.domain.repositories_intf.BankListRepository
import com.example.babybank.presentation.models.DownloadVia
import com.example.babybank.presentation.models.DownloadVia.DOWNLOAD_MANAGER
import com.example.babybank.presentation.models.DownloadVia.GET_RESPONSE
import io.reactivex.Completable
import javax.inject.Inject

class BankListRepositoryImpl @Inject constructor(
    private val downloadManagerDownloadDataSourceImpl: DownloadManagerDownloadDataSourceImpl,
    private val getResponseDownloadDataSourceImpl: GetResponseDownloadDataSourceImpl,
    private val mockDataSource: MockDataSource,
) : BankListRepository {

    override fun downloadFileVia(via: DownloadVia, directory: String): Completable {
        return mockDataSource.getPdfLink()
            .flatMapCompletable { pdfLinkDto ->
                val url = pdfLinkDto.url

                when (via) {
                    DOWNLOAD_MANAGER ->
                        downloadManagerDownloadDataSourceImpl.download(url, directory)
                    GET_RESPONSE ->
                        getResponseDownloadDataSourceImpl.download(url, directory)
                }
            }
    }

}
