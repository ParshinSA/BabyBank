package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.common.cicerone_router.FragmentRouter
import com.example.babybank.presentation.Screens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BtmSheetFragmentInWalletFrgViewModel @Inject constructor(
    routerProvider: FragmentRouter,
) : BaseViewModel() {
    private val router = routerProvider.router

    private val cardNumberMutLiveData = MutableLiveData("")
    val cardNumberLiveData: LiveData<String> get() = cardNumberMutLiveData

    private val moneyMutLiveData = MutableLiveData("")
    val moneyLiveData: LiveData<String> get() = moneyMutLiveData

    fun inputNumberCard(cardNumber: Observable<String>) {
        cardNumber
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userInput ->
                cardNumberMutLiveData.value = userInput
            }.autoClear()
    }

    fun inputMoney(money: Observable<String>) {
        money
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userInput ->
                moneyMutLiveData.value = userInput
            }.autoClear()
    }

    fun openDetailsReport() {
        val balanceTransfer = checkNotNull(moneyLiveData.value)
        val infoMessage = checkNotNull(cardNumberLiveData.value)

        router.navigateTo(Screens.DetailsTransferFrg(balanceTransfer, infoMessage), false)
    }

}