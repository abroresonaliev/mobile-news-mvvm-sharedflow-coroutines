package uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended.RecommendedArticlesViewModel
import uz.icerbersoft.mobilenews.presentation.support.viewmodel.ViewModelFactory
import uz.icerbersoft.mobilenews.presentation.support.viewmodel.ViewModelKey

@Module(includes = [RecommendedArticlesDaggerModule.Binder::class])
internal object RecommendedArticlesDaggerModule {

    @Module
    interface Binder {

        @Binds
        @RecommendedArticlesDaggerScope
        fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @RecommendedArticlesDaggerScope
        @ViewModelKey(RecommendedArticlesViewModel::class)
        fun viewModel(viewModel: RecommendedArticlesViewModel): ViewModel
    }
}