package com.example.babybank.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.babybank.R
import com.example.babybank.databinding.FragmentContainerBinding
import com.example.babybank.domain.models.CurrencyTypeDomain
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.Screens
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class ContainerFragment : BaseFragment() {

    private var _binding: FragmentContainerBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private val navigator: Navigator by lazy {
        AppNavigator(requireActivity(), R.id.frameLayoutContainer, childFragmentManager)
    }

    private var lastClickMenuItemId: Int? = null
    private var lastOpenFragment: FragmentScreen? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContainerBinding.inflate(layoutInflater, container, false)
        action()
        return binding.root
    }

    override fun onResume() {
        navigatorHolder.setNavigator(navigator)
        openDefaultFragment()
        super.onResume()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun openDefaultFragment() {
        if (childFragmentManager.findFragmentById(R.id.frameLayoutContainer) == null)
            openFrgByMenuItem(R.id.homeIdInBottomNavMenu)
    }

    private fun action() {
        listenerBottomNavigation()
    }

    private fun listenerBottomNavigation() {
        binding.bottomNavigationViewContainerFrg.setOnItemSelectedListener { menuItem ->
            openFrgByMenuItem(menuItem.itemId)
        }
    }

    private fun openFrgByMenuItem(menuItemId: Int): Boolean {
        return when (menuItemId) {
            R.id.homeIdInBottomNavMenu -> replaceFragment(Screens.HomeFrg(), menuItemId)
            R.id.walletIdInBottomNavMenu -> replaceFragment(Screens.WalletFrg(), menuItemId)
            R.id.balanceIdInBottomNavMenu -> replaceFragment(Screens.BalanceFrg(), menuItemId)
            R.id.profileIdInBottomNavMenu -> replaceFragment(Screens.ProfileFrg(), menuItemId)
            else -> {
                true
            }
        }
    }

    private fun replaceFragment(frg: FragmentScreen, menuItemId: Int? = null): Boolean {
        if (menuItemId == null) {
            navigator.applyCommands(arrayOf(Replace(frg)))
        }
        if (lastClickMenuItemId != menuItemId) {
            navigator.applyCommands(arrayOf(Replace(frg)))
            lastClickMenuItemId = menuItemId
            lastOpenFragment = frg
        }
        return true
    }

    fun openDetailsFragment(balance: String, currencyType: CurrencyTypeDomain?) {
        changeStateBottomNavView(false)
        navigator.applyCommands(arrayOf(Replace(Screens.DetailsFrg(balance, currencyType))))
    }

    fun closeDetailsFragment() {
        changeStateBottomNavView(true)
        replaceFragment(checkNotNull(lastOpenFragment))
    }

    private fun changeStateBottomNavView(state: Boolean) {
        binding.bottomNavigationViewContainerFrg.isVisible = state
    }

    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        fun newInstance() = ContainerFragment()
    }
}