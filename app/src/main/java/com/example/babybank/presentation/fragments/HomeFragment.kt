package com.example.babybank.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babybank.databinding.FragmentHomeBinding
import com.example.babybank.domain.models.CurrencyTypeDomain
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.adapters.*
import com.example.babybank.presentation.models.*
import com.example.babybank.presentation.viewmodels.BaseFactoryViewModelFactory
import com.example.babybank.presentation.viewmodels.HomeFrgViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: BaseFactoryViewModelFactory
    private val viewModel: HomeFrgViewModel by viewModels { viewModelFactory }

    private val accountAndCardMenuAdapterRv by lazy {
        AsyncListDifferDelegationAdapter(
            RecyclerViewAdapter.DiffUtilItemCallback(),
            AdapterDelegatesManager(
                AccountIconUiAdapterDelegateRv(this::click),
                CardUiAdapterDelegateRv(this::click),
                MenuTitleUiAdapterDelegateRv(),
                LoaderUiDelegateAdapterRv(),
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
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
        viewModel.totalMoneyLiveData.observe(viewLifecycleOwner) { totalMoney ->
            setTotalMoney(totalMoney)
        }

        viewModel.dataAccountsCardsLiveData.observe(viewLifecycleOwner) { dataList ->
            setDataAccountsCardsInRecyclerView(dataList)
        }

        viewModel.personalInfoLiveData.observe(viewLifecycleOwner) { personalInfo ->
            setupHelloUsername(personalInfo)
        }
    }

    private fun setTotalMoney(totalMoney: TotalMoneyUi) {
        binding.textViewTotalMoney.text = totalMoney.money
    }

    private fun setupHelloUsername(personalInfo: PersonalInfoHomeFrgUi) {
        binding.textViewHelloUsername.text = personalInfo.name
    }

    private fun setDataAccountsCardsInRecyclerView(dataList: List<DisplayableItem>) {
        with(binding.recyclerViewAccountsCards) {
            adapter = accountAndCardMenuAdapterRv
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        accountAndCardMenuAdapterRv.items =
            if (dataList.isEmpty()) listOf(LoaderUiRv()) else dataList
    }

    override fun click(itemId: Int) {
        val selectedButton =
            viewModel.dataAccountsCardsLiveData.value?.first { it.idItem == itemId }

        when (selectedButton) {

            is AccountIconUi -> openDetailsFrg(
                selectedButton.balance,
                CurrencyTypeDomain.values().first { it.icon == selectedButton.currencyTypeIcon })

            is CardUi -> openDetailsFrg(selectedButton.balance)
        }
    }

    private fun openDetailsFrg(balance: String, currencyType: CurrencyTypeDomain? = null) {
        (parentFragment as ContainerFragment).openDetailsFragment(balance, currencyType)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}