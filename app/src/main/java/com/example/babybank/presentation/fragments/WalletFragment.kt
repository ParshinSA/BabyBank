package com.example.babybank.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babybank.R
import com.example.babybank.databinding.FragmentWalletBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.adapters.*
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.LoaderUiRv
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import com.example.babybank.presentation.viewmodels.WalletFrgViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import javax.inject.Inject

class WalletFragment : BaseFragment() {

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: WalletFrgViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var factoryAdapter: FactoryDelegationAdapterDisplayableItem

    private val transferMenuAdapterRv by lazy {
        factoryAdapter.createAdapter(createDelegatesManagerTransferMenu())
    }

    private val operationMenuAdapterRv by lazy {
        factoryAdapter.createAdapter(createDelegatesManagerOperationMenu())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(layoutInflater, container, false)
        parentFrgBottomNavViewIsVisible(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    private fun action() {
        observeData()
    }

    private fun observeData() {
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { idErrorMessage ->
            showErrorDialog(idErrorMessage)
        }

        viewModel.operationsLiveDta.observe(viewLifecycleOwner) { menuItemList ->
            setDataOperationRv(menuItemList)
        }

        viewModel.transfersLiveDta.observe(viewLifecycleOwner) { menuItemList ->
            setDataTransferRv(menuItemList)
        }
    }

    private fun createDelegatesManagerTransferMenu(): AdapterDelegatesManager<List<DisplayableItem>> {
        return AdapterDelegatesManager(TransferUiAdapterDelegateRv(this::click))
    }

    private fun createDelegatesManagerOperationMenu(): AdapterDelegatesManager<List<DisplayableItem>> {
        return AdapterDelegatesManager(
            MenuItemTitleIconUiAdapterDelegateRv { idItem -> click(idItem) },
            MenuTitleUiAdapterDelegateRv(),
            LoaderUiDelegateAdapterRv()
        )
    }

    private fun setDataTransferRv(menuItemList: List<DisplayableItem>) {
        val recyclerView = binding.recyclerViewTransfers
        recyclerView.adapter = transferMenuAdapterRv
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        transferMenuAdapterRv.items = menuItemList
    }

    private fun setDataOperationRv(menuItemList: List<DisplayableItem>) {
        val recyclerView = binding.recyclerViewOperations
        recyclerView.adapter = operationMenuAdapterRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        operationMenuAdapterRv.items =
            if (menuItemList.isEmpty()) listOf(LoaderUiRv()) else menuItemList
    }

    // в зависимости от ситуации скрываем bottom nav view
    override fun click(itemId: Int) {
        when (itemId) {
            R.drawable.ic_to_a_card -> openBottomSheet()
            R.drawable.ic_swift_transfer -> openBankListFragment()
            else -> openDefaultFrg(itemId)
        }
    }

    private fun openDefaultFrg(itemId: Int) {
        viewModel.openDetailsFrg(itemId)
        hideBottomNavMenu()
    }

    private fun hideBottomNavMenu() {
        parentFrgBottomNavViewIsVisible(false)
    }

    private fun openBankListFragment() {
        viewModel.openBankListFragment()
        hideBottomNavMenu()
    }

    private fun openBottomSheet() {
        val modalBottomSheet = BtmSheetFragmentInWalletFrg.newInstance()
        modalBottomSheet.show(childFragmentManager, BtmSheetFragmentInWalletFrg.TAG_BTM_SHEET)
    }

    private fun parentFrgBottomNavViewIsVisible(state: Boolean) {
        (parentFragment as ContainerFragment).bottomNavViewIsVisible(state)
    }


    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        fun newInstance() = WalletFragment()
    }
}