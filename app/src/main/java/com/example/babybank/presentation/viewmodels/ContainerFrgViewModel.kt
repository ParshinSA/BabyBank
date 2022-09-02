package com.example.babybank.presentation.viewmodels

import com.example.babybank.R
import com.example.babybank.presentation.Screens
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class ContainerFrgViewModel @Inject constructor() : BaseViewModel() {

    fun getFragment(menuItemId: Int): FragmentScreen {
        return try {
            when (menuItemId) {
                R.id.homeIdInBottomNavMenu -> Screens.HomeFrg()
                R.id.walletIdInBottomNavMenu -> Screens.WalletFrg()
                R.id.balanceIdInBottomNavMenu -> Screens.BalanceFrg()
                R.id.profileIdInBottomNavMenu -> Screens.ProfileFrg()
                else -> {
                    error("Unknown menu item id $menuItemId")
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            showErrorMessage()
            Screens.HomeFrg()
        }
    }

}