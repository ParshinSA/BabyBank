package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.R
import com.example.babybank.domain.interactors.WalletFrgInteractor
import com.example.babybank.domain.models.MenuTypeDomain.OPERATIONS_MENU
import com.example.babybank.domain.models.MenuTypeDomain.TRANSFERS_MENU
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.presentation.models.DisplayableItem
import com.example.babybank.presentation.models.MenuTitleUi
import com.example.babybank.presentation.models.toMenuItemTitleIconUi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WalletFrgViewModel @Inject constructor(
    private val interactor: WalletFrgInteractor,
) : BaseViewModel() {

    private val operationsMutLiveDta = MutableLiveData<List<DisplayableItem>>(emptyList())
    val operationsLiveDta: LiveData<List<DisplayableItem>> get() = operationsMutLiveDta

    private val transfersMutLiveDta = MutableLiveData<List<DisplayableItem>>(emptyList())
    val transfersLiveDta: LiveData<List<DisplayableItem>> get() = transfersMutLiveDta

    private fun getMenu(request: RequestMenu) {
        interactor.getMenu(request)
            .map { menuItemDomainList ->
                menuItemDomainList.map { it.toMenuItemTitleIconUi() }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { menuItemList ->
                    when (request.menuType) {
                        OPERATIONS_MENU -> operationsMutLiveDta.value =
                            listOf(MenuTitleUi(R.string.textOperations)) + menuItemList
                        TRANSFERS_MENU -> transfersMutLiveDta.value = menuItemList
                        else -> error("Incorrect menu type ")
                    }
                }, { error ->
                    error.printStackTrace()
                    showErrorMessage()
                }).autoClear()
    }

    init {
        getMenu(RequestMenu(OPERATIONS_MENU))
        getMenu(RequestMenu(TRANSFERS_MENU))
    }

}