package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.data.common.utils.ExternalDownloadFolder
import com.github.terrakok.cicerone.Router
import java.io.File
import javax.inject.Inject

class PdfViewerFrgViewModel @Inject constructor(
    private var externalDownloadFolder: ExternalDownloadFolder,
    private val parentRouter: Router
) : BaseViewModel() {

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
        val file = externalDownloadFolder.getFileExternalFolder(directory)?.get(0)
        file?.let { openPdfMutLiveData.value = it }
    }

    fun backPressed(): Boolean {
        parentRouter.exit()
        return true
    }

}