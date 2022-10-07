package com.example.babybank.presentation.fragments

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babybank.databinding.FragmentBankListBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.adapters.FactoryDelegationAdapterDisplayableItem
import com.example.babybank.presentation.adapters.UploadedFileUiAdapterRv
import com.example.babybank.presentation.common.BackButtonListener
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.DownloadVia.DOWNLOAD_MANAGER
import com.example.babybank.presentation.models.DownloadVia.GET_RESPONSE
import com.example.babybank.presentation.models.OpenVia.CURRENT_APPLICATION
import com.example.babybank.presentation.models.OpenVia.THIRD_PARTY_APPLICATION
import com.example.babybank.presentation.models.UploadedFileUi
import com.example.babybank.presentation.viewmodels.BankListFrgViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import javax.inject.Inject

class BankListFragment : BaseFragment(), BackButtonListener {

    private var _binding: FragmentBankListBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: BankListFrgViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var factoryAdapter: FactoryDelegationAdapterDisplayableItem
    private val adapterRv by lazy { factoryAdapter.createAdapter(createDelegatesManager()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    private fun action() {
        initFileListRecyclerView()
        downloadBankList()
        loadFileViaListener()
        openFileViaListener()
        observeData()
    }

    private fun initFileListRecyclerView() {
        with(binding.recyclerViewFileList) {
            adapter = adapterRv
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun loadFileViaListener() {
        val switchDownloadManager = binding.switchDownloadManager
        val switchGetResponse = binding.switchGetResponse

        switchDownloadManager.setOnClickListener {
            switchGetResponse.isChecked = !switchDownloadManager.isChecked
            changeDownloadFileVia()
        }

        switchGetResponse.setOnClickListener {
            switchDownloadManager.isChecked = !switchGetResponse.isChecked
            changeDownloadFileVia()
        }
    }

    private fun openFileViaListener() {
        val switchCurrentApplication = binding.switchCurrentApplication
        val switchThirdPartyApplication = binding.switchThirdPartyApplication

        switchCurrentApplication.setOnClickListener {
            switchThirdPartyApplication.isChecked = !switchCurrentApplication.isChecked
            changeOpenFileVia()
        }

        switchThirdPartyApplication.setOnClickListener {
            switchCurrentApplication.isChecked = !switchThirdPartyApplication.isChecked
            changeOpenFileVia()
        }
    }

    private fun createDelegatesManager(): AdapterDelegatesManager<List<DisplayableItem>> {
        return AdapterDelegatesManager(UploadedFileUiAdapterRv(this::openFile))
    }

    private fun changeDownloadFileVia() {
        viewModel.changeDownloadFileVia(
            if (binding.switchDownloadManager.isChecked) DOWNLOAD_MANAGER else GET_RESPONSE
        )
    }

    private fun changeOpenFileVia() {
        viewModel.changeOpenFileVia(
            if (binding.switchThirdPartyApplication.isChecked) THIRD_PARTY_APPLICATION else CURRENT_APPLICATION
        )
    }

    private fun observeData() {
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            changeStateBtnDownload(!isLoading)
        }

        viewModel.uploadedFilesLiveData.observe(viewLifecycleOwner) { uploadedFiles ->
            updateRv(uploadedFiles)
        }
    }

    private fun updateRv(uploadedFiles: List<UploadedFileUi>) {
        adapterRv.items = uploadedFiles
    }

    private fun changeStateBtnDownload(state: Boolean) {
        binding.appCompatButtonLoadFile.isEnabled = state
    }

    private fun downloadBankList() {
        binding.appCompatButtonLoadFile.setOnClickListener {
            viewModel.downloadBankList()
        }
    }

    private fun openFile(fileName: String) {
        when (checkNotNull(viewModel.openVia)) {
            THIRD_PARTY_APPLICATION -> openFileThirdPartyApplication(fileName)
            CURRENT_APPLICATION -> openViaCurrentApplication(fileName)
        }
    }

    private fun openFileThirdPartyApplication(fileName: String) {
        val file = viewModel.getFile(fileName) ?: return
        val intent = viewModel.getIntentActionView(file)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            viewModel.showErrorMessage()
        }
    }

    private fun openViaCurrentApplication(fileName: String) {
        viewModel.openPdfViewerFrg(fileName)
    }

    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        fun newInstance() = BankListFragment()
    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackPressed()
        return true
    }
}