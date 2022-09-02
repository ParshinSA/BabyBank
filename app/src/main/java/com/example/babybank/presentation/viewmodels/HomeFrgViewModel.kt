package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.R
import com.example.babybank.common.constants.CONTAINER_FRAGMENT_ROUTER
import com.example.babybank.common.extentions.toStringMoneyFormat
import com.example.babybank.domain.interactors.HomeFrgInteractor
import com.example.babybank.domain.models.AccountInfoDomain
import com.example.babybank.domain.models.CurrencyTypeDomain
import com.example.babybank.domain.models.RequestCurrencyRate
import com.example.babybank.presentation.Screens
import com.example.babybank.presentation.models.*
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class HomeFrgViewModel @Inject constructor(
    private val interactor: HomeFrgInteractor,
    @Named(CONTAINER_FRAGMENT_ROUTER)
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

    init {
        interactor.getPersonalInformation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ personalInfo ->
                personalInfoMutLiveData.value = personalInfo.toPersonalInfoHomeFrg()
            }, { error ->
                error.printStackTrace()
                showErrorMessage()
            }).autoClear()

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
        /* Данные размещенны согласно порядку установки в recyclerView HomeFrg */
        dataAccountsCardsMutLiveData.value = (
                listOf(MenuTitleUi(R.string.textAccounts))
                        + dataList.toAccountIconUiList()
                        + listOf(MenuTitleUi(R.string.textCards))
                        + dataList.toCardUiList()
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
                            "${CurrencyTypeDomain.RUB.name} " + resultTotal.toStringMoneyFormat()
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

        try {
            val screen = when (selectedButton) {
                is AccountIconUi -> {
                    Screens.DetailsFrg(
                        selectedButton.balance,
                        CurrencyTypeDomain.values()
                            .first { it.icon == selectedButton.currencyTypeIcon }.symbol
                    )
                }
                is CardUi -> Screens.DetailsFrg(selectedButton.balance)
                else -> error("Unknown button $selectedButton")
            }
            parentRouter.navigateTo(screen)
        } catch (t: Throwable) {
            t.printStackTrace()
        }

    }

}