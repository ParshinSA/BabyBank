package com.example.babybank.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.babybank.R
import com.example.babybank.common.constants.CONTAINER_FRAGMENT_ROUTER
import com.example.babybank.presentation.Screens
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject
import javax.inject.Named

class ContainerFrgViewModel @Inject constructor(
    @Named(CONTAINER_FRAGMENT_ROUTER)
    private val router: Router
) : ViewModel() {

    private var lastClickMenuItemId: Int? = null
    private var lastOpenFragment: FragmentScreen? = null


    override fun onCleared() {
        Log.d("MyTAG", "onCleared: container")
        super.onCleared()
    }

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
            Screens.HomeFrg()
        }
    }

}