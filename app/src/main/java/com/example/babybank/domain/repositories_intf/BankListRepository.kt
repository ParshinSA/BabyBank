package com.example.babybank.domain.repositories_intf

import com.example.babybank.presentation.models.DownloadVia
import io.reactivex.Completable

interface BankListRepository {
    fun downloadFileVia(via: DownloadVia): Completable
}