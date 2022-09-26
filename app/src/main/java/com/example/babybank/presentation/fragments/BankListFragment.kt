package com.example.babybank.presentation.fragments

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babybank.databinding.FragmentBankListBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.adapters.RecyclerViewAdapter
import com.example.babybank.presentation.adapters.UploadedFileUiAdapterRv
import com.example.babybank.presentation.common.BackButtonListener
import com.example.babybank.presentation.models.DownloadVia
import com.example.babybank.presentation.models.UploadedFileUi
import com.example.babybank.presentation.viewmodels.BankListFrgViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import javax.inject.Inject

class BankListFragment : BaseFragment(), BackButtonListener {

    private var _binding: FragmentBankListBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: BankListFrgViewModel by viewModels { viewModelFactory }

    private val uploadedFilesRecyclerViewAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            RecyclerViewAdapter.DiffUtilItemCallback(),
            AdapterDelegatesManager(UploadedFileUiAdapterRv(this::open))
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

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
        downloadVia()
        observeData()
    }

    private fun initFileListRecyclerView() {
        with(binding.recyclerViewFileList) {
            adapter = uploadedFilesRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun downloadVia() {
        val switchDownloadManager = binding.switchDownloadManager
        val switchGetResponse = binding.switchGetResponse

        switchDownloadManager.setOnClickListener {
            switchGetResponse.isChecked = !switchDownloadManager.isChecked
            viewModel.changeDownloadVia(DownloadVia.DOWNLOAD_MANAGER)
        }

        switchGetResponse.setOnClickListener {
            switchDownloadManager.isChecked = !switchGetResponse.isChecked
            viewModel.changeDownloadVia(DownloadVia.GET_RESPONSE)
        }
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
        uploadedFilesRecyclerViewAdapter.items = uploadedFiles
    }

    private fun changeStateBtnDownload(state: Boolean) {
        binding.appCompatButtonLoadFile.isEnabled = state
    }

    private fun downloadBankList() {
        binding.appCompatButtonLoadFile.setOnClickListener {
            viewModel.downloadBankList()
        }
    }

    private fun open(fileName: String) {

        val file = viewModel.getFile(fileName)

        if (file == null) {
            viewModel.showErrorMessage()
            return
        }

        val target = viewModel.createTarget(file)
        val intent = Intent.createChooser(target, "Open File")

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.d(TAG, "open: fail")
            viewModel.showErrorMessage()
        }
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