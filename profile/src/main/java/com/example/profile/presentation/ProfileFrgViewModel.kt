package com.example.profile.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.profile.R
import com.example.profile.domain.interactor.ProfileFrgInteractor
import com.example.profile.domain.models.MenuTypeDomain
import com.example.profile.presentation.intefaces.DisplayableItem
import com.example.profile.presentation.models.ConvertersDomainToUi
import com.example.profile.presentation.models.MenuTitleUi
import com.example.profile.presentation.models.PersonalInfoProfileFrgUi
import com.example.profile.presentation.models.RequestMenu
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfileFrgViewModel(
    private val interactor: ProfileFrgInteractor,
    private val converters: ConvertersDomainToUi,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val errorMessageMutLiveData = MutableLiveData<Int>()
    val errorMessageLiveData: LiveData<Int> get() = errorMessageMutLiveData

    private val customAvatarLinkMutLiveData = MutableLiveData<String?>()
    val customAvatarLinkLiveData: LiveData<String?> get() = customAvatarLinkMutLiveData

    private val personalInfoMutLiveData = MutableLiveData<PersonalInfoProfileFrgUi>()
    val personalInfoLiveData: LiveData<PersonalInfoProfileFrgUi>
        get() = personalInfoMutLiveData

    private val menuItemMutLiveData = MutableLiveData<List<DisplayableItem>>(emptyList())
    val menuItemLiveDta: LiveData<List<DisplayableItem>> get() = menuItemMutLiveData

    init {
        interactor.getPersonalInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ personalInfoDomain ->
                personalInfoMutLiveData.value =
                    converters.toPersonalInfoProfileFrgUi(personalInfoDomain)
            }, { error ->
                error.printStackTrace()
                showErrorMessage()
            }).autoClear()

        interactor.getMenu(RequestMenu(MenuTypeDomain.SETTINGS_MENU))
            .map { menuItemDomainList ->
                menuItemDomainList.map { item -> converters.toMenuItemTitleIconUi(item) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ menuItemTitleIconList ->
                menuItemMutLiveData.value =
                    listOf(MenuTitleUi(R.string.settings)) + menuItemTitleIconList
            }, { error ->
                error.printStackTrace()
                showErrorMessage()
            }).autoClear()

        interactor.checkCustomAvatarLink()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ uriString: String? ->
                setCustomAvatar(uriString)
            }, {
                customAvatarLinkMutLiveData.value = null
                it.printStackTrace()
            }).autoClear()
    }

    fun saveCustomAvatarLink(uri: Uri?) {
        if (uri != null) {
            interactor.saveCustomAvatarLink(uri)
                .subscribeOn(Schedulers.io())
                .subscribe().autoClear()
        }
    }

    fun setCustomAvatar(uriString: String?) {
        uriString?.let { customAvatarLinkMutLiveData.value = it }
    }

    private fun showErrorMessage() {
        errorMessageMutLiveData.value = R.string.errorMessage
    }

    private fun Disposable.autoClear() {
        compositeDisposable.add(this)
    }
}