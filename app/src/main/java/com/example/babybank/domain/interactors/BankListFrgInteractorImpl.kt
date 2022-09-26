package com.example.babybank.domain.interactors

import com.example.babybank.domain.repositories_intf.BankListRepository
import com.example.babybank.presentation.models.DownloadVia
import io.reactivex.Completable
import javax.inject.Inject

class BankListFrgInteractorImpl @Inject constructor(
    private val bankListRepository: BankListRepository
) : BankListFrgInteractor {

    override fun downloadFileVia(via: DownloadVia): Completable {
        return bankListRepository.downloadFileVia(via)
    }
}