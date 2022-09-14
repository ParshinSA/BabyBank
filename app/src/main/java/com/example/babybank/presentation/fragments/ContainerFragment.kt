package com.example.babybank.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.babybank.R
import com.example.babybank.databinding.FragmentContainerBinding
import com.example.babybank.presentation.AppApplication
import com.example.babybank.presentation.viewmodels.ContainerFrgViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class ContainerFragment : BaseFragment() {

    private var _binding: FragmentContainerBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private val fragmentManger: FragmentManager by lazy { childFragmentManager }
    private val idContainer = R.id.frameLayoutContainer
    private val navigator: Navigator by lazy {
        AppNavigator(requireActivity(), idContainer, fragmentManger)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ContainerFrgViewModel by viewModels { viewModelFactory }

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
        Log.d(TAG, "onResume: ")
        navigatorHolder.setNavigator(navigator)
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

    private fun action() {
        openStartFragment()
        listenerBottomNavigation()
    }

    private fun openStartFragment() {
        openFragment(R.id.homeIdInBottomNavMenu)
    }

    private fun listenerBottomNavigation() {
        binding.bottomNavigationViewContainerFrg.setOnItemSelectedListener { menuItem ->
            openFragment(menuItem.itemId)
            true
        }
    }

    private fun openFragment(itemId: Int) {
        val newFragmentTag = itemId.toString()
        val transaction = fragmentManger.beginTransaction()
        val newFragment = fragmentManger.findFragmentByTag(newFragmentTag)
        var currentFragment: Fragment? = null

        for (fragment in fragmentManger.fragments) {
            if (fragment.isVisible) currentFragment = fragment
        }

        if (currentFragment != null && currentFragment === newFragment) return

        if (newFragment == null) {
            transaction.add(
                idContainer,
                viewModel.getFragment(itemId).createFragment(fragmentManger.fragmentFactory),
                newFragmentTag
            )
        }

        currentFragment?.let { transaction.hide(it) }
        newFragment?.let { transaction.show(it) }
        transaction.commitNow()
    }

    fun bottomNavViewIsVisible(state: Boolean) {
        binding.bottomNavigationViewContainerFrg.isVisible = state
    }

    override fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        fun newInstance() = ContainerFragment()
    }
}