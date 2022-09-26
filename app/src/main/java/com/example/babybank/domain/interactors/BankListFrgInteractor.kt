package com.example.babybank.domain.interactors

import com.example.babybank.presentation.models.DownloadVia
import io.reactivex.Completable

interface BankListFrgInteractor {

    fun downloadFileVia(via: DownloadVia): Completable
}
