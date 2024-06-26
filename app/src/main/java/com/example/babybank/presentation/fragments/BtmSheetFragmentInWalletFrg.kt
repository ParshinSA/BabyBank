package com.example.babybank.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.babybank.R
import com.example.babybank.databinding.FragmentBtmSheetToACardBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.common.FourDigitCardFormatWatcher
import com.example.babybank.presentation.common.FourDigitCardFormatWatcher.Companion.MAX_NUMBER_DIGITS
import com.example.babybank.presentation.common.MoneyFormatWatcher
import com.example.babybank.presentation.common.MoneyFormatter
import com.example.babybank.presentation.viewmodels.BtmSheetFragmentInWalletFrgViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class BtmSheetFragmentInWalletFrg : BottomSheetDialogFragment() {

    private var _binding: FragmentBtmSheetToACardBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: BtmSheetFragmentInWalletFrgViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var formatter: MoneyFormatter

    @Inject
    lateinit var numberCardFormat: FourDigitCardFormatWatcher

    @Inject
    lateinit var moneyFormat: MoneyFormatWatcher

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentBtmSheetToACardBinding.inflate(layoutInflater, container, false)
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

            val isValidateCard = validateCardNumber(cardNumber)
            val isValidateMoney = validateMoney(money)

            if (isValidateCard && isValidateMoney) {
                this.dismiss()
                viewModel.openDetailsReport()
            }
        }
    }

    private fun validateMoney(money: String): Boolean {
        val validate: Boolean
        val moneyNumberFormat = formatter.toNumberMoneyFormat(money)?.toDouble() ?: 0.0

        binding.txtInputLayoutContainerMoney.error =
            if (money.isNotEmpty() && money.isNotBlank() && moneyNumberFormat > 0.0) {
                validate = true
                null
            } else {
                validate = false
                resources.getString(R.string.requiredToFill)
            }

        return validate
    }

    private fun validateCardNumber(cardNumber: String): Boolean {
        val cardNumberTotalDigits = cardNumber.filter { char: Char -> Character.isDigit(char) }
        val validate: Boolean

        binding.txtInputLayoutContainerCardNumber.error =
            when {
                cardNumberTotalDigits.isEmpty() -> {
                    validate = false
                    resources.getString(R.string.requiredToFill)
                }

                cardNumberTotalDigits.length < MAX_NUMBER_DIGITS -> {
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



