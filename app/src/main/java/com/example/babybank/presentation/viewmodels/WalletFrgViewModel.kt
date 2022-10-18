package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.R
import com.example.babybank.common.cicerone_router.FragmentRouter
import com.example.babybank.domain.interactors.WalletFrgInteractor
import com.example.babybank.domain.models.MenuTypeDomain.OPERATIONS_MENU
import com.example.babybank.domain.models.MenuTypeDomain.TRANSFERS_MENU
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.presentation.Screens
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.ConvertersDomainToUi
import com.example.babybank.presentation.models.MenuTitleUi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WalletFrgViewModel @Inject constructor(
    private val interactor: WalletFrgInteractor,
    private val converters: ConvertersDomainToUi,
    routerProvider: FragmentRouter,
) : BaseViewModel() {
    private val router = routerProvider.router

    private val operationsMutLiveDta = MutableLiveData<List<DisplayableItem>>(emptyList())
    val operationsLiveDta: LiveData<List<DisplayableItem>> get() = operationsMutLiveDta

    private val transfersMutLiveDta = MutableLiveData<List<DisplayableItem>>(emptyList())
    val transfersLiveDta: LiveData<List<DisplayableItem>> get() = transfersMutLiveDta

    private fun getMenu(request: RequestMenu) {
        interactor.getMenu(request)
            .map { menuItemDomainList ->
                menuItemDomainList.map { item -> converters.toMenuItemTitleIconUi(item) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { menuItemList ->
                    when (request.menuType) {
                        OPERATIONS_MENU -> operationsMutLiveDta.value =
                            listOf(MenuTitleUi(R.string.operations)) + menuItemList
                        TRANSFERS_MENU -> transfersMutLiveDta.value = menuItemList
                        else -> error("Incorrect menu type ")
                    }
                }, { error ->
                    error.printStackTrace()
                    showErrorMessage()
                }).autoClear()
    }

    fun openDetailsFrg(itemId: Int) {
        router.navigateTo(Screens.DetailsTransferFrg("$itemId", "test"))
    }

    fun openBankListFragment() {
        router.navigateTo(Screens.BankListFrg())
    }

    init {
        getMenu(RequestMenu(OPERATIONS_MENU))
        getMenu(RequestMenu(TRANSFERS_MENU))
    }

}