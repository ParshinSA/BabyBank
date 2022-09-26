package com.example.babybank.common.di

import android.content.Context
import com.example.babybank.common.di.modules.*
import com.example.babybank.presentation.ContainerActivity
import com.example.babybank.presentation.fragments.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        DataSourceModule::class,
        NavigationModule::class,
        NetworkModule::class,
        AppModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: ContainerActivity)

    fun inject(fragment: BtmSheetFragmentInWalletFrg)
    fun inject(fragment: DetailsTransitionFragment)
    fun inject(fragment: OnboardingFragment)
    fun inject(fragment: ContainerFragment)
    fun inject(fragment: BankListFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: WalletFragment)
    fun inject(fragment: HomeFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}