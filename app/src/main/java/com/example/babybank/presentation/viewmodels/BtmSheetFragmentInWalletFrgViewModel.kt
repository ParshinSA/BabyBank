package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.common.constants.CONTAINER_FRAGMENT_ROUTER
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class BtmSheetFragmentInWalletFrgViewModel @Inject constructor(
    @Named(CONTAINER_FRAGMENT_ROUTER)
    private val parentRouter: Router
) : BaseViewModel() {

    private val cardNumberMutLiveData = MutableLiveData("")
    val cardNumberLiveData: LiveData<String> get() = cardNumberMutLiveData

    private val moneyMutLiveData = MutableLiveData("")
    val moneyLiveData: LiveData<String> get() = moneyMutLiveData

    fun inputNumberCard(userInputCardNumber: BehaviorSubject<String>) {
        userInputCardNumber
            .distinctUntilChanged()
            .debounce(350, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userInput ->
                cardNumberMutLiveData.value = userInput
            }.autoClear()
    }

    fun inputMoney(userInputAmountMoney: BehaviorSubject<String>) {
        userInputAmountMoney
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userInput ->
                moneyMutLiveData.value = userInput
            }.autoClear()
    }

    fun openDetailsReport() {
        TODO("Not yet implemented")
    }

}