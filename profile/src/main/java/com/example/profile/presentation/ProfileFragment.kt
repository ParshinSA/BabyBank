package com.example.profile.presentation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.profile.R
import com.example.profile.common.di.ProfileComponentViewModel
import com.example.profile.databinding.FragmentProfileBinding
import com.example.profile.presentation.adapters.LoaderUiDelegateAdapterRv
import com.example.profile.presentation.adapters.MenuItemTitleIconUiAdapterDelegateRv
import com.example.profile.presentation.adapters.MenuTitleUiAdapterDelegateRv
import com.example.profile.presentation.adapters.RecyclerViewAdapter
import com.example.profile.presentation.intefaces.DisplayableItem
import com.example.profile.presentation.models.LoaderUiRv
import com.example.profile.presentation.models.PersonalInfoProfileFrgUi
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private val component: ProfileComponentViewModel by viewModels()

    private var toast: Toast? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ProfileFrgViewModel by viewModels { viewModelFactory }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.saveCustomAvatarLink(uri)
            viewModel.setCustomAvatar(uri.toString())
        }

    private val adapterRv = AsyncListDifferDelegationAdapter(
        RecyclerViewAdapter.DiffUtilItemCallback(),
        createDelegatesManager()
    )

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

    private fun click(itemId: Int) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), "Click $itemId", Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun setImageInAvatar(uriString: String?) {
        Glide.with(requireContext()).load(uriString)
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

    private fun setPhoneNumber(phoneNumber: String) {
        binding.textViewUserPhoneNumber.text = phoneNumber
    }

    private fun setupSettingsMenu(menuItemList: List<DisplayableItem>) {
        val recyclerView = binding.recyclerViewProfileInfo
        recyclerView.adapter = adapterRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        adapterRv.items = if (menuItemList.isEmpty()) listOf(LoaderUiRv()) else menuItemList
    }

    override fun onDestroy() {
        _binding = null
        toast = null
        super.onDestroy()
    }

    private fun inject() {
        component.component.inject(this)
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}