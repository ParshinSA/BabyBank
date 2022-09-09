package com.example.babybank.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.babybank.R
import com.example.babybank.databinding.FragmentBtmSheetInWalletFragmentBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.common.FourDigitCardFormatWatcher
import com.example.babybank.presentation.common.MoneyFormatWatcher
import com.example.babybank.presentation.viewmodels.BaseFactoryViewModelFactory
import com.example.babybank.presentation.viewmodels.BtmSheetFragmentInWalletFrgViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class BtmSheetFragmentInWalletFrg : BottomSheetDialogFragment() {

    private var _binding: FragmentBtmSheetInWalletFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: BaseFactoryViewModelFactory
    private val viewModel: BtmSheetFragmentInWalletFrgViewModel by viewModels { viewModelFactory }

    private val numberCardFormat: FourDigitCardFormatWatcher by lazy { FourDigitCardFormatWatcher() }
    private val moneyFormat: MoneyFormatWatcher by lazy { MoneyFormatWatcher() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBtmSheetInWalletFragmentBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    private fun action() {
        listenerUserInput()
        transferBtnClick()
        observe()
    }

    private fun observe() {
        viewModel.cardNumberLiveData.observe(viewLifecycleOwner) {
            binding.txtInputLayoutContainerCardNumber.error = null
        }

        viewModel.moneyLiveData.observe(viewLifecycleOwner) {
            binding.txtInputLayoutContainerMoney.error = null
        }
    }

    private fun transferBtnClick() {
        binding.appCompatButtonTransfer.setOnClickListener {
            val cardNumber = checkNotNull(viewModel.cardNumberLiveData.value)
            val money = checkNotNull(viewModel.moneyLiveData.value)

            if (validateCardNumber(cardNumber) && validateMoney(money)) {
                Log.d("TAG", "transferBtnClick: validate true")
                viewModel.openDetailsReport()
            } else {
                Log.d("TAG", "transferBtnClick: validate false")
            }
        }
    }

    private fun validateMoney(money: String): Boolean {
        val validate: Boolean

        binding.txtInputLayoutContainerMoney.error =
            if (money.isNotEmpty() && money.isNotBlank() && money.toDouble() > 0.0) {
                validate = true
                null
            } else {
                validate = false
                resources.getString(R.string.requiredToFill)
            }

        return validate
    }

    private fun validateCardNumber(cardNumber: String): Boolean {
        val validate: Boolean

        binding.txtInputLayoutContainerCardNumber.error =
            when {
                cardNumber.isEmpty() -> {
                    validate = false
                    resources.getString(R.string.requiredToFill)
                }

                cardNumber.length < 16 -> {
                    validate = false
                    resources.getString(R.string.incorrectFormatCardNumber)
                }
                else -> {
                    validate = true
                    null
                }
            }

        return validate
    }

    private fun listenerUserInput() {
        binding.txtInputLayoutContainerCardNumber.editText?.addTextChangedListener(numberCardFormat)
        viewModel.inputNumberCard(numberCardFormat.getUserInputCardNumber())

        binding.txtInputLayoutContainerMoney.editText?.addTextChangedListener(moneyFormat)
        viewModel.inputMoney(moneyFormat.getUserInputAmountMoney())
    }

    fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        const val TAG_BTM_SHEET = "TAG_BTM_SHEET"
        fun newInstance() = BtmSheetFragmentInWalletFrg()
    }
}



