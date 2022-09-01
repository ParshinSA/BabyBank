package com.example.babybank.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.babybank.R
import com.example.babybank.presentation.viewmodels.AppActivityViewModel
import com.example.babybank.presentation.viewmodels.BaseFactoryViewModelFactory
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject


class AppActivity : AppCompatActivity(R.layout.activity_app) {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator: Navigator = AppNavigator(this, R.id.appContainer)

    private var exitDialog: AlertDialog? = null

    @Inject
    lateinit var viewModelFactory: BaseFactoryViewModelFactory
    private val viewModel: AppActivityViewModel by viewModels { viewModelFactory }

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
        if (supportFragmentManager.findFragmentById(R.id.appContainer) == null)
            viewModel.startOnboarding()
    }

    override fun onBackPressed() {
        if (viewModel.isExitAppLiveData.value!!) viewModel.onBackPressed() else {
            showExitDialog()
            viewModel.firstClickExitApp()
            exitDialog?.show()
        }
    }

    private fun showExitDialog() {
        exitDialog = AlertDialog.Builder(this)
            .setMessage(resources.getString(R.string.textExitDialogMessage))
            .create()
    }

    private fun inject() {
        (applicationContext as AppApplication).appComponent.inject(this)
    }

}
