package uz.icebergsoft.mobilenews.presentation.presentation.setttings.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.icebergsoft.mobilenews.presentation.presentation.setttings.SettingsViewModel
import uz.icebergsoft.mobilenews.presentation.support.viewmodel.ViewModelFactory
import uz.icebergsoft.mobilenews.presentation.support.viewmodel.ViewModelKey

@Module(includes = [SettingsDaggerModule.Binder::class])
internal object SettingsDaggerModule {

    @Module
    interface Binder {

        @Binds
        @SettingsDaggerScope
        fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @SettingsDaggerScope
        @ViewModelKey(SettingsViewModel::class)
        fun viewModel(viewModel: SettingsViewModel): ViewModel
    }
}