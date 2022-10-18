package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.common.cicerone_router.FragmentRouter
import com.example.babybank.data.common.utils.AppExternalStorage
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class PdfViewerFrgViewModel @Inject constructor(
    private var appExternalStorage: AppExternalStorage,
    routerProvider: FragmentRouter,
) : BaseViewModel() {
    private val router = routerProvider.router

    private val toolbarTitleMutLiveData = MutableLiveData<String>()
    val toolbarTitleLiveData: LiveData<String> get() = toolbarTitleMutLiveData

    private val openPdfMutLiveData = MutableLiveData<File>()
    val openPdfLiveData: LiveData<File> get() = openPdfMutLiveData

    fun setArguments(fileName: String, directory: String) {
        setToolbarTitle(fileName)
        defineFile(fileName, directory)
    }

    private fun setToolbarTitle(fileName: String) {
        toolbarTitleMutLiveData.value = fileName
    }

    private fun defineFile(fileName: String, directory: String) {
        appExternalStorage.getFileFromExternalFolder(fileName, directory)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ file: File ->
                openPdfMutLiveData.value = file
            }, { it.printStackTrace() }).autoClear()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}