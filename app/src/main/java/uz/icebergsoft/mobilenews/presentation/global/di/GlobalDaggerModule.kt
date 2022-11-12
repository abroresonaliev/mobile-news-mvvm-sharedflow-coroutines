package uz.icebergsoft.mobilenews.presentation.global.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.icebergsoft.mobilenews.presentation.global.GlobalViewModel
import uz.icebergsoft.mobilenews.presentation.support.viewmodel.ViewModelFactory
import uz.icebergsoft.mobilenews.presentation.support.viewmodel.ViewModelKey

@Module(includes = [GlobalDaggerModule.Binder::class])
object GlobalDaggerModule {

    @Module
    interface Binder {

        @Binds
        @GlobalScope
        fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @GlobalScope
        @ViewModelKey(GlobalViewModel::class)
        fun viewModel(viewModel: GlobalViewModel): ViewModel

    }
}