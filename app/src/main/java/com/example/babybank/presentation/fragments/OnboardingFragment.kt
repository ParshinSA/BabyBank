package com.example.babybank.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.babybank.databinding.FragmentOnboardingBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.viewmodels.OnboardingFrgViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class OnboardingFragment : BaseFragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: OnboardingFrgViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    private fun action() {
        fabNextListener()
    }

    private fun fabNextListener() {
        binding.floatingActionButtonNext.setOnClickListener {
            viewModel.fabNextClick()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        fun newInstance() = OnboardingFragment()
    }
}