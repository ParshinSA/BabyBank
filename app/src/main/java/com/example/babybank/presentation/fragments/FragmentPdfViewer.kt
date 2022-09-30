package com.example.babybank.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.babybank.common.extentions.withArguments
import com.example.babybank.databinding.FragmentPdfViewerBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.common.BackButtonListener
import com.example.babybank.presentation.viewmodels.PdfViewerFrgViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import java.io.File
import javax.inject.Inject

class FragmentPdfViewer : BaseFragment(), BackButtonListener {

    private var _binding: FragmentPdfViewerBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: PdfViewerFrgViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    private fun action() {
        getArgument()
        observeData()
    }

    private fun getArgument() {
        val nameFile = requireArguments().getString(KEY_FILE_NAME, "")
        val directory = requireArguments().getString(KEY_DIRECTORY, "")
        viewModel.setArguments(nameFile, directory)
    }

    private fun observeData() {
        viewModel.openPdfLiveData.observe(viewLifecycleOwner) { file ->
            openPdf(file)
        }

        viewModel.toolbarTitleLiveData.observe(viewLifecycleOwner) { nameFile ->
            setTitleToolbar(nameFile)
        }
    }

    private fun setTitleToolbar(nameFile: String) {
        binding.toolbar.title = nameFile
    }

    private fun openPdf(file: File) {
        val t =binding.PDFView.display.mode

        binding.PDFView.fromFile(file)
            .load()
    }

    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        private const val KEY_FILE_NAME = "KEY_FILE_NAME"
        private const val KEY_DIRECTORY = "KEY_DIRECTORY"

        fun newInstance(fileName: String, directory: String): FragmentPdfViewer {
            return FragmentPdfViewer().withArguments {
                putString(KEY_FILE_NAME, fileName)
                putString(KEY_DIRECTORY, directory)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return viewModel.backPressed()
    }
}