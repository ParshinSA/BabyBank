package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.R
import com.example.babybank.common.constants.CONTAINER_FRAGMENT_ROUTER
import com.example.babybank.domain.interactors.DetailsTransitionFrgInteractor
import com.example.babybank.domain.models.MenuTypeDomain
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.ConvertersDomainToUi
import com.example.babybank.presentation.models.MenuTitleUi
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class DetailsTransitionFrgViewModel @Inject constructor(
    interactor: DetailsTransitionFrgInteractor,
    @Named(CONTAINER_FRAGMENT_ROUTER)
    private val parentRouter: Router,
    private val converters: ConvertersDomainToUi
) : BaseViewModel() {

    private val menuItemMutLiveData = MutableLiveData<List<DisplayableItem>>(emptyList())
    val menuItemLiveDta: LiveData<List<DisplayableItem>> get() = menuItemMutLiveData

    private val balanceMutLiveData = MutableLiveData<String>()
    val balanceLiveData: LiveData<String> get() = balanceMutLiveData

    private val messageMutLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = messageMutLiveData

    init {
        interactor.getMenu(RequestMenu(MenuTypeDomain.ACTIONS_MENU))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { menuItemDomainList ->
                menuItemDomainList.map { item -> converters.toMenuItemTitleIconUi(item) }
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
        balanceMutLiveData.value = balanceMessage
    }

    fun onBackPressed() {
        parentRouter.exit()
    }

    fun setMessage(message: String) {
        messageMutLiveData.value = message
    }
}