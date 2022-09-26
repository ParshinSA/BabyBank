package com.example.babybank.presentation.viewmodels

import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.domain.interactors.BankListFrgInteractor
import com.example.babybank.presentation.models.DownloadVia
import com.example.babybank.presentation.models.ExternalDownloadFolder
import com.example.babybank.presentation.models.UploadedFileUi
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject


class BankListFrgViewModel @Inject constructor(
    private val interactor: BankListFrgInteractor,
    private var externalDownloadFolder: ExternalDownloadFolder,
    private val parentRouter: Router
) : BaseViewModel() {

    private var loadingLine = DownloadVia.DOWNLOAD_MANAGER

    private val isLoadingMutLiveData = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean> get() = isLoadingMutLiveData

    private val uploadedFilesMutLiveData = MutableLiveData<List<UploadedFileUi>>(emptyList())
    val uploadedFilesLiveData: LiveData<List<UploadedFileUi>> get() = uploadedFilesMutLiveData

    // Start update uploaded file
    init {
        updateUploadedFileList()
    }

    fun downloadBankList() {
        isLoadingMutLiveData.value = true

        interactor.downloadFileVia(loadingLine)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateUploadedFileList()
                isLoadingMutLiveData.value = false
            }, {
                isLoadingMutLiveData.value = false
                it.printStackTrace()
            }).autoClear()
    }

    private fun updateUploadedFileList() {
        val fileList = getFileList() ?: return
        uploadedFilesMutLiveData.value = fileList.map { file: File ->
            UploadedFileUi(file.name)
        }
    }

    private fun getFileList(): Array<out File>? {
        return externalDownloadFolder.getFileExternalFolder()
    }

    fun changeDownloadVia(via: DownloadVia) {
        loadingLine = via
    }

    fun onBackPressed() {
        parentRouter.exit()
    }

    fun createTarget(file: File): Intent {
        val uri = Uri.parse(file.absolutePath)
        val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)

        val target = Intent()
        target.action = Intent.ACTION_VIEW
        target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        target.setDataAndType(uri, mimetype)

        return target
    }

    fun getFile(fileName: String): File? {
        return getFileList()?.filter { file -> file.name == fileName }?.get(0)
    }

}