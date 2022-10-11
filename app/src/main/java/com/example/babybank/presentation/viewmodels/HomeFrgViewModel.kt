package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.R
import com.example.babybank.domain.interactors.HomeFrgInteractor
import com.example.babybank.domain.models.AccountInfoDomain
import com.example.babybank.domain.models.CurrencyTypeDomain
import com.example.babybank.domain.models.RequestCurrencyRate
import com.example.babybank.presentation.Screens
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.common.MoneyFormatter
import com.example.babybank.presentation.models.*
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeFrgViewModel @Inject constructor(
    private val converters: ConvertersDomainToUi,
    private val moneyFormatter: MoneyFormatter,
    private val interactor: HomeFrgInteractor,
    private val parentRouter: Router
) : BaseViewModel() {

    private val personalInfoMutLiveData = MutableLiveData<PersonalInfoHomeFrgUi>()
    val personalInfoLiveData: LiveData<PersonalInfoHomeFrgUi> get() = personalInfoMutLiveData

    private val totalMoneyMutLiveData = MutableLiveData<TotalMoneyUi>()
    val totalMoneyLiveData: LiveData<TotalMoneyUi> get() = totalMoneyMutLiveData

    private val dataAccountsCardsMutLiveData =
        MutableLiveData<List<DisplayableItem>>(emptyList())
    val dataAccountsCardsLiveData: LiveData<List<DisplayableItem>>
        get() = dataAccountsCardsMutLiveData

    fun updateInfo() {
        getAccountInfo()
        getPersonalInfo()
    }

    private fun getPersonalInfo() {
        interactor.getPersonalInformation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ personalInfo ->
                personalInfoMutLiveData.value = converters.toPersonalInfoHomeFrg(personalInfo)
            }, { error ->
                error.printStackTrace()
                showErrorMessage()
            }).autoClear()
    }

    private fun getAccountInfo() {
        interactor.getAccountsInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ accountInfoDomainList ->
                convertAndSetData(accountInfoDomainList)
                toTotalMoney(accountInfoDomainList)
            }, { error ->
                error.printStackTrace()
                showErrorMessage()
            }).autoClear()
    }

    private fun convertAndSetData(dataList: List<AccountInfoDomain>) {
        dataAccountsCardsMutLiveData.value = (
                listOf(MenuTitleUi(R.string.accounts))/* Данные размещенны согласно порядку установки в recyclerView HomeFrg */
                        + converters.toAccountIconUiList(dataList)
                        + listOf(MenuTitleUi(R.string.cards))
                        + converters.toCardUiList(dataList)
                )
    }

    private fun toTotalMoney(accountList: List<AccountInfoDomain>) {
        var resultTotal = 0.0
        accountList.forEach { accountInfoDomain ->
            val request =
                RequestCurrencyRate(CurrencyTypeDomain.valueOf(accountInfoDomain.currencyType))

            interactor.getCurrencyRate(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyRateDomain ->
                    synchronized(this) {
                        resultTotal += accountInfoDomain.balance * currencyRateDomain.value
                    }
                    totalMoneyMutLiveData.value =
                        TotalMoneyUi(
                            "${CurrencyTypeDomain.RUB.name} " +
                                    moneyFormatter.toStringMoneyFormat(resultTotal)
                        )
                }, { error ->
                    error.printStackTrace()
                    showErrorMessage()
                }).autoClear()
        }
    }

    fun openDetailsFrg(itemId: Int) {
        val selectedButton =
            dataAccountsCardsLiveData.value?.first { it.idItem == itemId } ?: return

        parentRouter.navigateTo(Screens.DetailsTransferFrg(createBalanceString(selectedButton)))
    }

    private fun createBalanceString(item: DisplayableItem): String {
        return try {
            when (item) {
                is AccountIconUi -> {
                    val balance = item.balance

                    val symbol = CurrencyTypeDomain.values()
                        .first { it.icon == item.currencyTypeIcon }.symbol

                    "$symbol $balance"
                }
                is CardUi -> item.balance
                else -> error("Unknown button $item")
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            ""
        }
    }

}