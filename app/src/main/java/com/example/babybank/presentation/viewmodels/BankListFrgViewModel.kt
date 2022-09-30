package com.example.babybank.presentation.viewmodels

import android.content.Intent
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babybank.data.common.utils.ExternalDownloadFolder
import com.example.babybank.data.common.utils.ExternalDownloadFolder.Companion.FOLDER_BANK_LIST
import com.example.babybank.domain.interactors.BankListFrgInteractor
import com.example.babybank.presentation.Screens
import com.example.babybank.presentation.common.FileUriProvider
import com.example.babybank.presentation.models.DownloadVia
import com.example.babybank.presentation.models.OpenVia
import com.example.babybank.presentation.models.UploadedFileUi
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class BankListFrgViewModel @Inject constructor(
    private var externalDownloadFolder: ExternalDownloadFolder,
    private val interactor: BankListFrgInteractor,
    private val fileUriProvider: FileUriProvider,
    private val parentRouter: Router,
) : BaseViewModel() {

    private val downloadFolder = FOLDER_BANK_LIST

    private var loadVia = DownloadVia.DOWNLOAD_MANAGER

    var openVia = OpenVia.CURRENT_APPLICATION
    private set

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

        interactor.downloadFileVia(loadVia, downloadFolder)
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
        return externalDownloadFolder.getFileExternalFolder(downloadFolder)
    }

    fun changeDownloadFileVia(via: DownloadVia) {
        loadVia = via
    }

    fun changeOpenFileVia(via: OpenVia) {
        openVia = via
    }

    fun onBackPressed() {
        parentRouter.exit()
    }

    fun getFile(fileName: String): File? {
        return getFileList()?.filter { file -> file.name == fileName }?.get(0)
    }

    fun openPdfViewerFrg(fileName: String) {
        parentRouter.navigateTo(Screens.PDFViewerFrg(fileName, downloadFolder))
    }


    fun getIntentActionView(file: File): Intent {
        val uri = fileUriProvider.getUri(file)
        val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)

        val target = Intent(Intent.ACTION_VIEW)
        target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        target.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        target.setDataAndType(uri, mimetype)

        return Intent.createChooser(target, "Open File")
    }
}