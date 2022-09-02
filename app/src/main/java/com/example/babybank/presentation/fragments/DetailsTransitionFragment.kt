package com.example.babybank.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babybank.common.extentions.withArguments
import com.example.babybank.databinding.FragmentDetailsTransersBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.adapters.LoaderUiDelegateAdapterRv
import com.example.babybank.presentation.adapters.MenuItemTitleIconUiAdapterDelegateRv
import com.example.babybank.presentation.adapters.MenuTitleUiAdapterDelegateRv
import com.example.babybank.presentation.adapters.RecyclerViewAdapter
import com.example.babybank.presentation.models.DisplayableItem
import com.example.babybank.presentation.models.LoaderUiRv
import com.example.babybank.presentation.viewmodels.BaseFactoryViewModelFactory
import com.example.babybank.presentation.viewmodels.DetailsTransitionFrgViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import javax.inject.Inject

class DetailsTransitionFragment : BaseFragment() {

    private var _binding: FragmentDetailsTransersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: BaseFactoryViewModelFactory
    private val viewModel: DetailsTransitionFrgViewModel by viewModels { viewModelFactory }

    private val actionRecyclerViewAdapter by lazy {
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
        _binding = FragmentDetailsTransersBinding.inflate(layoutInflater, container, false)
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
        val currencySymbol = requireArguments().getString(KEY_CURRENCY_TYPE, "")
        viewModel.setBalanceMessage("$currencySymbol $balance")
    }

    private fun observeData() {
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { idErrorMessage ->
            showErrorDialog(idErrorMessage)
        }

        viewModel.menuItemLiveDta.observe(viewLifecycleOwner) { menuItemList ->
            setupActionMenu(menuItemList)
        }

        viewModel.balanceMessageLiveData.observe(viewLifecycleOwner) { balanceMessage ->
            binding.textViewBalanceMessage.text = balanceMessage
        }
    }

    private fun setupActionMenu(menuItemList: List<DisplayableItem>) {
        with(binding.recyclerViewActions) {
            adapter = actionRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        actionRecyclerViewAdapter.items =
            if (menuItemList.isEmpty()) listOf(LoaderUiRv()) else menuItemList
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
        private const val KEY_CURRENCY_TYPE = "KEY_CURRENCY_TYPE"

        fun newInstance(balance: String, currencySymbol: String?) =
            DetailsTransitionFragment().withArguments {
                putString(KEY_BALANCE, balance)
                currencySymbol?.let { putString(KEY_CURRENCY_TYPE, currencySymbol) }
            }
    }
}