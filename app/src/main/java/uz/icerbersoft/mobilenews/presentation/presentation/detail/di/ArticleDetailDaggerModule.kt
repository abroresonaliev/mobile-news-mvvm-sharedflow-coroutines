package uz.icerbersoft.mobilenews.presentation.presentation.detail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.icerbersoft.mobilenews.presentation.presentation.detail.ArticleDetailViewModel
import uz.icerbersoft.mobilenews.presentation.support.viewmodel.ViewModelFactory
import uz.icerbersoft.mobilenews.presentation.support.viewmodel.ViewModelKey

@Module(includes = [ArticleDetailDaggerModule.Binder::class])
internal object ArticleDetailDaggerModule {

    @Module
    interface Binder {

        @Binds
        @ArticleDetailDaggerScope
        fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @ArticleDetailDaggerScope
        @ViewModelKey(ArticleDetailViewModel::class)
        fun viewModel(viewModel: ArticleDetailViewModel): ViewModel
    }
}