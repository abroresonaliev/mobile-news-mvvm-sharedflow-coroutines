package uz.icebergsoft.mobilenews.presentation.presentation.home.features.readlater.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.icebergsoft.mobilenews.presentation.presentation.home.features.readlater.ReadLaterArticlesViewModel
import uz.icebergsoft.mobilenews.presentation.support.viewmodel.ViewModelFactory
import uz.icebergsoft.mobilenews.presentation.support.viewmodel.ViewModelKey

@Module(includes = [ReadLaterArticlesDaggerModule.Binder::class])
internal object ReadLaterArticlesDaggerModule {

    @Module
    interface Binder {

        @Binds
        @ReadLaterArticlesDaggerScope
        fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @ReadLaterArticlesDaggerScope
        @ViewModelKey(ReadLaterArticlesViewModel::class)
        fun viewModel(viewModel: ReadLaterArticlesViewModel): ViewModel
    }
}