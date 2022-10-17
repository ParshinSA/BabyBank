package com.example.babybank.presentation.models

import com.example.babybank.R
import com.example.babybank.domain.models.AccountInfoDomain
import com.example.babybank.domain.models.CardDomain
import com.example.babybank.domain.models.CurrencyTypeDomain.EUR
import com.example.babybank.domain.models.CurrencyTypeDomain.USD
import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.PersonalInfoDomain
import com.example.babybank.presentation.common.MoneyFormatter
import com.example.profile.presentation.models.PersonalInfoProfileFrgUi
import javax.inject.Inject

class ConvertersDomainToUi @Inject constructor(
    private val moneyFormatter: MoneyFormatter
) {
    fun toPersonalInfoProfileFrgUi(model: PersonalInfoDomain): PersonalInfoProfileFrgUi {
        return with(model) {
            PersonalInfoProfileFrgUi(
                name = "$lastName $name",
                phoneNumber = phoneNumber
            )
        }
    }

    fun toCardUiList(listModel: List<AccountInfoDomain>): List<CardUi> {
        return listModel.map { accountInfoDomain ->
            accountInfoDomain.cards.map { card ->
                createCardUi(card, accountInfoDomain)
            }
        }.flatten()
    }

    private fun createCardUi(cardDomain: CardDomain, accountInfoDomain: AccountInfoDomain): CardUi {
        val cardIdString = cardDomain.id.toString()
        return CardUi(
            shortId = cardIdString.substring(
                cardIdString.length - 4,
                cardIdString.length
            ),
            balance = moneyFormatter.toStringMoneyFormat(accountInfoDomain.balance) +
                    " " + accountInfoDomain.currencyType,
        )
    }

    fun toAccountIconUiList(listModel: List<AccountInfoDomain>): List<AccountIconUi> {
        return listModel.map { accountInfoDomain ->
            AccountIconUi(
                balance = moneyFormatter.toStringMoneyFormat(accountInfoDomain.balance),
                currencyTypeIcon =
                when (accountInfoDomain.currencyType) {
                    EUR.name -> EUR.icon
                    USD.name -> USD.icon
                    else -> 0
                }
            )
        }
    }

    fun toPersonalInfoHomeFrg(personalInfoDomain: PersonalInfoDomain) =
        PersonalInfoHomeFrgUi(personalInfoDomain.name)

    fun toMenuItemTitleIconUi(menuItemDomain: MenuItemDomain): MenuItemTitleIconUi {
        return MenuItemTitleIconUi(
            title = menuItemDomain.title,
            idIcon = when (menuItemDomain.idIcon) {

                "Ask for a transfer" -> R.drawable.ic_ask_for_a_transfer
                "SWIFT transfer" -> R.drawable.ic_swift_transfer
                "Transfers by requisites" -> R.drawable.ic_transfers_by_requisites
                "Payment by QR code" -> R.drawable.ic_payments_by_qr_code
                "Currency exchange" -> R.drawable.ic_currency_exchange

                "To a card" -> R.drawable.ic_to_a_card
                "By phone" -> R.drawable.ic_by_phone
                "Inside the bank" -> R.drawable.ic_inside_the_bank
                "To another bank" -> R.drawable.ic_to_another_bank

                "Save a receipt" -> R.drawable.ic_save_a_receipt
                "Create a template" -> R.drawable.ic_create_a_template

                else -> R.drawable.ic_error_load_image
            }
        )
    }

}
