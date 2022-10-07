package com.example.babybank.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.babybank.presentation.adapters.FactoryDelegationAdapterDisplayableItem
import com.example.babybank.R
import com.example.babybank.databinding.FragmentProfileBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.adapters.LoaderUiDelegateAdapterRv
import com.example.babybank.presentation.adapters.MenuItemTitleIconUiAdapterDelegateRv
import com.example.babybank.presentation.adapters.MenuTitleUiAdapterDelegateRv
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.LoaderUiRv
import com.example.babybank.presentation.models.PersonalInfoProfileFrgUi
import com.example.babybank.presentation.viewmodels.ProfileFrgViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ProfileFrgViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var factoryAdapter: FactoryDelegationAdapterDisplayableItem

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent(), this::checkUri)

    private val adapterRv by lazy { factoryAdapter.createAdapter(createDelegatesManager()) }

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
        selectedImageFromGalleryInAvatar()
    }

    private fun observeData() {
        viewModel.personalInfoLiveData.observe(viewLifecycleOwner) { personalInfo ->
            setPersonalInfo(personalInfo)
        }

        viewModel.menuItemLiveDta.observe(viewLifecycleOwner) { menuItemList ->
            setupSettingsMenu(menuItemList)
        }

        viewModel.customAvatarLinkLiveData.observe(viewLifecycleOwner) { uriString: String? ->
            setImageInAvatar(uriString)
        }
    }

    private fun checkUri(uri: Uri?) {
        uri?.let {
            setImageInAvatar(uri.toString())
            viewModel.saveCustomAvatarLink(uri)
        }
    }

    private fun selectedImageFromGalleryInAvatar() {
        binding.imageViewChangePhoto.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun createDelegatesManager(): AdapterDelegatesManager<List<DisplayableItem>> {
        return AdapterDelegatesManager(
            MenuItemTitleIconUiAdapterDelegateRv(this::click),
            MenuTitleUiAdapterDelegateRv(),
            LoaderUiDelegateAdapterRv()
        )
    }

    private fun setImageInAvatar(uriString: String?) {
        Glide.with(requireContext())
            .load(uriString)
            .optionalCenterCrop()
            .placeholder(R.drawable.ic_load_image)
            .error(R.drawable.ic_face)
            .into(binding.shapeImageViewAvatarProfile)
    }

    private fun setPersonalInfo(personalInfo: PersonalInfoProfileFrgUi) {
        setPhoneNumber(personalInfo.phoneNumber)
        setUsername(personalInfo.name)
    }

    private fun setUsername(name: String) {
        binding.textViewUsername.text = name
    }

    private fun setAvatar(avatarLink: String) {
        Glide.with(requireContext())
            .load(avatarLink)
            .placeholder(R.drawable.ic_load_image)
            .error(R.drawable.ic_error_load_image)
            .into(binding.shapeImageViewAvatarProfile)
    }

    private fun setPhoneNumber(phoneNumber: String) {
        binding.textViewUserPhoneNumber.text = phoneNumber
    }

    private fun setupSettingsMenu(menuItemList: List<DisplayableItem>) {
        with(binding.recyclerViewProfileInfo) {
            adapter = adapterRv
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        adapterRv.items = if (menuItemList.isEmpty()) listOf(LoaderUiRv()) else menuItemList
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