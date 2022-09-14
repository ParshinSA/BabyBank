package com.example.babybank.presentation.fragments

import android.os.Bundle
import android.util.Log
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
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import javax.inject.Inject

class WalletFragment : BaseFragment() {

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: WalletFrgViewModel by viewModels { viewModelFactory }

    private val transferMenuAdapterRv by lazy {
        AsyncListDifferDelegationAdapter(
            RecyclerViewAdapter.DiffUtilItemCallback(),
            AdapterDelegatesManager(
                TransferUiAdapterDelegateRv(this::click)
            )
        )
    }

    private val operationMenuAdapterRv by lazy {
        AsyncListDifferDelegationAdapter(
            RecyclerViewAdapter.DiffUtilItemCallback(),
            AdapterDelegatesManager(
                MenuItemTitleIconUiAdapterDelegateRv { idItem -> click(idItem) },
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
        _binding = FragmentWalletBinding.inflate(layoutInflater, container, false)
        parentFrgBottomNavViewIsVisible(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    override fun onResume() {
        Log.d("MyTAG", "onResume: init wallet")
        super.onResume()
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

    private fun setDataTransferRv(menuItemList: List<DisplayableItem>) {
        with(binding.recyclerViewTransfers) {
            adapter = transferMenuAdapterRv
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
        transferMenuAdapterRv.items = menuItemList
    }

    private fun setDataOperationRv(menuItemList: List<DisplayableItem>) {
        with(binding.recyclerViewOperations) {
            adapter = operationMenuAdapterRv
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        operationMenuAdapterRv.items =
            if (menuItemList.isEmpty()) listOf(LoaderUiRv()) else menuItemList
    }

    override fun click(itemId: Int) {
        if (itemId == R.drawable.ic_swift_transfer) {
            val modalBottomSheet = BtmSheetFragmentInWalletFrg.newInstance()
            modalBottomSheet.show(childFragmentManager, BtmSheetFragmentInWalletFrg.TAG_BTM_SHEET)
            return
        }
        viewModel.openDetailsFrg(itemId)
        parentFrgBottomNavViewIsVisible(false)
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