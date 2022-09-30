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

    fun DetailsTransferFrg(
        balanceAndSymbol: String,
        message: String? = null
    ) = FragmentScreen {
        DetailsTransitionFragment.newInstance(
            balanceAndSymbol = balanceAndSymbol,
            message = message
        )
    }

    fun BankListFrg() = FragmentScreen {
        BankListFragment.newInstance()
    }

    fun PDFViewerFrg(fileName: String, directory: String) = FragmentScreen {
        FragmentPdfViewer.newInstance(fileName = fileName, directory = directory)
    }
}