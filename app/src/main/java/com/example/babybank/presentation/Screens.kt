package com.example.babybank.presentation

import com.example.babybank.presentation.fragments.*
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun OnboardingFrg() = FragmentScreen {
        OnboardingFragment.newInstance()
    }

    fun ContainerFrg() = FragmentScreen {
        ContainerFragment.newInstance()
    }

    fun HomeFrg() = FragmentScreen {
        HomeFragment.newInstance()
    }

    fun WalletFrg() = FragmentScreen {
        WalletFragment.newInstance()
    }

    fun ProfileFrg() = FragmentScreen {
        ProfileFragment.newInstance()
    }

    fun BalanceFrg() = FragmentScreen {
        BalanceFragment.newInstance()
    }

    fun DetailsFrg(balance: String, currencySymbol: String? = null) = FragmentScreen {
        DetailsTransitionFragment.newInstance(balance, currencySymbol)
    }
}