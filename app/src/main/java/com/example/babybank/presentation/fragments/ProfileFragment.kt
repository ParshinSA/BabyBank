package com.example.babybank.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.babybank.R
import com.example.babybank.databinding.FragmentProfileBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.adapters.LoaderUiDelegateAdapterRv
import com.example.babybank.presentation.adapters.MenuItemTitleIconUiAdapterDelegateRv
import com.example.babybank.presentation.adapters.MenuTitleUiAdapterDelegateRv
import com.example.babybank.presentation.adapters.RecyclerViewAdapter
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.LoaderUiRv
import com.example.babybank.presentation.models.PersonalInfoProfileFrgUi
import com.example.babybank.presentation.viewmodels.BaseFactoryViewModelFactory
import com.example.babybank.presentation.viewmodels.ProfileFrgViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: BaseFactoryViewModelFactory
    private val viewModel: ProfileFrgViewModel by viewModels { viewModelFactory }

    private val settingsMenuAdapterRv by lazy {
        AsyncListDifferDelegationAdapter(
            RecyclerViewAdapter.DiffUtilItemCallback(),
            AdapterDelegatesManager(
                MenuItemTitleIconUiAdapterDelegateRv(this::click),
                MenuTitleUiAdapterDelegateRv(),
                LoaderUiDelegateAdapterRv()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        action()
        return binding.root
    }

    private fun action() {
        observeData()
    }

    private fun observeData() {
        viewModel.personalInfoLiveData.observe(viewLifecycleOwner) { personalInfo ->
            setPersonalInfo(personalInfo)
        }

        viewModel.menuItemLiveDta.observe(viewLifecycleOwner) { menuItemList ->
            setupSettingsMenu(menuItemList)
        }
    }

    private fun setPersonalInfo(personalInfo: PersonalInfoProfileFrgUi) {
        Glide.with(requireContext())
            .load(personalInfo.avatarLink)
            .placeholder(R.drawable.ic_load_image)
            .error(R.drawable.ic_error_load_image)
            .into(binding.shapeImageViewAvatarProfile)

        binding.textViewUserPhoneNumber.text = personalInfo.phoneNumber
        binding.textViewUsername.text = personalInfo.name
    }

    private fun setupSettingsMenu(menuItemList: List<DisplayableItem>) {
        with(binding.recyclerViewProfileInfo) {
            adapter = settingsMenuAdapterRv
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        settingsMenuAdapterRv.items =
            if (menuItemList.isEmpty()) listOf(LoaderUiRv()) else menuItemList
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}