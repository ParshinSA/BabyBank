package com.example.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profile.domain.interactor.ProfileFrgInteractor
import com.example.profile.presentation.models.ConvertersDomainToUi
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val profileFrgInteractor: ProfileFrgInteractor,
    private val converters: ConvertersDomainToUi,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(ProfileFrgViewModel::class.java) ->
                ProfileFrgViewModel(
                    interactor = profileFrgInteractor,
                    converters = converters
                ) as T

            else -> error("Incorrect view model factory $modelClass")
        }
    }
}