package com.example.babybank.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babybank.common.extentions.withArguments
import com.example.babybank.databinding.FragmentDetailsTransfersBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.adapters.FactoryDelegationAdapterDisplayableItem
import com.example.babybank.presentation.adapters.LoaderUiDelegateAdapterRv
import com.example.babybank.presentation.adapters.MenuItemTitleIconUiAdapterDelegateRv
import com.example.babybank.presentation.adapters.MenuTitleUiAdapterDelegateRv
import com.example.babybank.presentation.common.BackButtonListener
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.LoaderUiRv
import com.example.babybank.presentation.viewmodels.DetailsTransitionFrgViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import javax.inject.Inject

class DetailsTransitionFragment : BaseFragment(), BackButtonListener {

    private var _binding: FragmentDetailsTransfersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DetailsTransitionFrgViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var factoryAdapter: FactoryDelegationAdapterDisplayableItem
    private val adapterRv by lazy { factoryAdapter.createAdapter(createDelegatesManager()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsTransfersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun action() {
        getArgument()
        observeData()
        doneClick()
    }

    private fun getArgument() {
        val balance = requireArguments().getString(KEY_BALANCE, "")
        val message = requireArguments().getString(KEY_MESSAGE, "")
        viewModel.setBalanceMessage(balance)
        viewModel.setMessage(message)
    }

    private fun observeData() {
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { idErrorMessage ->
            showErrorDialog(idErrorMessage)
        }

        viewModel.menuItemLiveDta.observe(viewLifecycleOwner) { menuItemList ->
            setupActionMenu(menuItemList)
        }

        viewModel.balanceLiveData.observe(viewLifecycleOwner) { balanceMessage ->
            binding.textViewBalanceMessage.text = balanceMessage
        }

        viewModel.messageLiveData.observe(viewLifecycleOwner) { message ->
            binding.textViewInfoMessage.text = message
        }
    }

    private fun createDelegatesManager(): AdapterDelegatesManager<List<DisplayableItem>> {
        return AdapterDelegatesManager(
            MenuItemTitleIconUiAdapterDelegateRv(this::click),
            MenuTitleUiAdapterDelegateRv(),
            LoaderUiDelegateAdapterRv()
        )
    }

    private fun setupActionMenu(menuItemList: List<DisplayableItem>) {
        val recyclerView = binding.recyclerViewActions
        recyclerView.adapter = adapterRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        adapterRv.items = if (menuItemList.isEmpty()) listOf(LoaderUiRv()) else menuItemList
    }

    private fun doneClick() {
        binding.appCompatButtonDone.setOnClickListener {
            viewModel.onBackPressed()
        }
    }

    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        private const val KEY_BALANCE = "KEY_BALANCE"
        private const val KEY_MESSAGE = "KEY_MESSAGE"

        fun newInstance(balanceAndSymbol: String, message: String?) =
            DetailsTransitionFragment().withArguments {
                putString(KEY_BALANCE, balanceAndSymbol)
                message?.let { putString(KEY_MESSAGE, message) }
            }
    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackPressed()
        return true
    }
}