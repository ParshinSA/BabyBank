package com.example.profile.presentation.models

import com.example.profile.R
import com.example.profile.domain.models.MenuItemDomain
import com.example.profile.domain.models.PersonalInfoDomain
import javax.inject.Inject

class ConvertersDomainToUi @Inject constructor() {
    fun toPersonalInfoProfileFrgUi(model: PersonalInfoDomain): PersonalInfoProfileFrgUi {
        return with(model) {
            PersonalInfoProfileFrgUi(
                name = "$lastName $name",
                phoneNumber = phoneNumber
            )
        }
    }

    fun toMenuItemTitleIconUi(menuItemDomain: MenuItemDomain): MenuItemTitleIconUi {
        return MenuItemTitleIconUi(
            title = menuItemDomain.title,
            idIcon = when (menuItemDomain.idIcon) {

                "Theme of the app" -> R.drawable.ic_theme_of_the_app
                "Change password" -> R.drawable.ic_change_password
                "Documents" -> R.drawable.ic_documents
                "Security" -> R.drawable.ic_security
                "COVID-19 QR code" -> R.drawable.ic_covid_19_qr_code

                else -> R.drawable.ic_error_load_image
            }
        )
    }

}
