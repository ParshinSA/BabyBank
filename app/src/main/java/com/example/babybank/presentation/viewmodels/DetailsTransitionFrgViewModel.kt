package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.R
import com.example.babybank.domain.interactors.DetailsTransitionFrgInteractor
import com.example.babybank.domain.models.MenuTypeDomain
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.presentation.models.DisplayableItem
import com.example.babybank.presentation.models.MenuTitleUi
import com.example.babybank.presentation.models.toMenuItemTitleIconUi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsTransitionFrgViewModel @Inject constructor(
    interactor: DetailsTransitionFrgInteractor
) : BaseViewModel() {

    private val menuItemMutLiveData = MutableLiveData<List<DisplayableItem>>(emptyList())
    val menuItemLiveDta: LiveData<List<DisplayableItem>> get() = menuItemMutLiveData

    private val balanceMessageMutLiveData = MutableLiveData<String>()
    val balanceMessageLiveData: LiveData<String> get() = balanceMessageMutLiveData

    init {
        interactor.getMenu(RequestMenu(MenuTypeDomain.ACTIONS_MENU))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { menuItemDomainList ->
                menuItemDomainList.map { it.toMenuItemTitleIconUi() }
            }
            .subscribe({ menuItemTitleIconUiList ->
                menuItemMutLiveData.value =
                    listOf(MenuTitleUi(R.string.textActions)) + menuItemTitleIconUiList
            }, { error ->
                error.printStackTrace()
                showErrorMessage()
            }).autoClear()
    }

    fun setBalanceMessage(balanceMessage: String) {
        balanceMessageMutLiveData.value = balanceMessage
    }
}