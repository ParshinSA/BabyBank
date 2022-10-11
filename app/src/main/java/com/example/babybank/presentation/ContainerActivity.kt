package com.example.babybank.presentation

import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.babybank.R
import com.example.babybank.presentation.common.BackButtonListener
import com.example.babybank.presentation.viewmodels.ContainerActivityViewModel
import com.example.babybank.presentation.viewmodels.ViewModelFactory
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject


class ContainerActivity : AppCompatActivity(R.layout.activity_app) {

    @IdRes
    private val idNavigationContainer = R.id.appContainer
    private val navigator: Navigator = AppNavigator(this, idNavigationContainer)

    private var exitDialog: AlertDialog? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ContainerActivityViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onStart() {
        super.onStart()
        inject()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
        action()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun action() {
        if (supportFragmentManager.findFragmentById(idNavigationContainer) == null)
            viewModel.startOnboarding()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(idNavigationContainer)

        if (fragment != null && fragment is BackButtonListener && fragment.onBackPressed()) return

        if (
            viewModel.isExitAppLiveData.value!!
        ) viewModel.onBackPressed() else {
            showExitDialog()
            viewModel.firstClickExitApp()
            exitDialog?.show()
        }
    }

    private fun showExitDialog() {
        exitDialog = AlertDialog.Builder(this)
            .setMessage(resources.getString(R.string.exitDialogMessage))
            .create()
    }

    private fun inject() {
        (applicationContext as AppApplication).appComponent.inject(this)
    }

}
