package com.example.babybank.presentation.models

import com.example.babybank.R
import com.example.babybank.common.extentions.toStringMoneyFormat
import com.example.babybank.domain.models.AccountInfoDomain
import com.example.babybank.domain.models.CurrencyTypeDomain.EUR
import com.example.babybank.domain.models.CurrencyTypeDomain.USD
import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.PersonalInfoDomain
import java.util.*

fun PersonalInfoDomain.toPersonalInfoProfileFrgUi(): PersonalInfoProfileFrgUi {
    return PersonalInfoProfileFrgUi(
        name = "$lastName $name",
        avatarLink = avatarLink,
        phoneNumber = phoneNumber
    )
}

fun List<AccountInfoDomain>.toCardUiList(): List<CardUi> {
    val list = LinkedList<CardUi>()

    this.forEach { accountInfoDomain ->
        accountInfoDomain.cards.forEach { card ->
            val cardIdString = card.id.toString()
            list.add(
                CardUi(
                    shortId = cardIdString.substring(
                        cardIdString.length - 4,
                        cardIdString.length
                    ),
                    balance = accountInfoDomain.balance.toStringMoneyFormat() + " " + accountInfoDomain.currencyType,
                )
            )
        }
    }

    return list
}

fun List<AccountInfoDomain>.toAccountIconUiList(): List<AccountIconUi> {
    return this.map { accountInfoDomain ->
        AccountIconUi(
            balance = accountInfoDomain.balance.toStringMoneyFormat(),
            currencyTypeIcon =
            when (accountInfoDomain.currencyType) {
                EUR.name -> EUR.icon
                USD.name -> USD.icon
                else -> 0
            }
        )
    }
}

fun PersonalInfoDomain.toPersonalInfoHomeFrg() = PersonalInfoHomeFrgUi(name)

fun MenuItemDomain.toMenuItemTitleIconUi(): MenuItemTitleIconUi {
    return MenuItemTitleIconUi(
        title = title,
        idIcon = when (idIcon) {

            "Ask for a transfer" -> R.drawable.ic_ask_for_a_transfer
            "SWIFT transfer" -> R.drawable.ic_swift_transfer
            "Transfers by requisites" -> R.drawable.ic_transfers_by_requisites
            "Payment by QR code" -> R.drawable.ic_payments_by_qr_code
            "Currency exchange" -> R.drawable.ic_currency_exchange

            "To a card" -> R.drawable.ic_to_a_card
            "By phone" -> R.drawable.ic_by_phone
            "Inside the bank" -> R.drawable.ic_inside_the_bank
            "To another bank" -> R.drawable.ic_to_another_bank

            "Theme of the app" -> R.drawable.ic_theme_of_the_app
            "Change password" -> R.drawable.ic_change_password
            "Documents" -> R.drawable.ic_documents
            "Security" -> R.drawable.ic_security
            "COVID-19 QR code" -> R.drawable.ic_covid_19_qr_code

            "Save a receipt" -> R.drawable.ic_save_a_receipt
            "Create a template" -> R.drawable.ic_create_a_template

            else -> R.drawable.ic_error_load_image
        }
    )
}

