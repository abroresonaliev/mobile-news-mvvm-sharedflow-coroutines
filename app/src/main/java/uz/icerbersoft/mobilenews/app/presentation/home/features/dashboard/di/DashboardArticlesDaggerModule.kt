package uz.icerbersoft.mobilenews.app.presentation.home.features.dashboard.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.icerbersoft.mobilenews.app.presentation.home.features.dashboard.DashboardArticlesViewModel
import uz.icerbersoft.mobilenews.app.support.viewmodel.ViewModelFactory
import uz.icerbersoft.mobilenews.app.support.viewmodel.ViewModelKey

@Module(includes = [DashboardArticlesDaggerModule.Binder::class])
internal object DashboardArticlesDaggerModule {

    @Module
    interface Binder {

        @Binds
        @DashboardArticlesDaggerScope
        fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @DashboardArticlesDaggerScope
        @ViewModelKey(DashboardArticlesViewModel::class)
        fun viewModel(viewModel: DashboardArticlesViewModel): ViewModel

    }
}