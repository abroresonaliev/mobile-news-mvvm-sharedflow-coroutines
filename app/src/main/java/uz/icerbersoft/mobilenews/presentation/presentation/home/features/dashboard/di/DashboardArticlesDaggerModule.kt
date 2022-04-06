package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.DashboardArticlesViewModel
import uz.icerbersoft.mobilenews.presentation.support.viewmodel.ViewModelKey

@Module(includes = [DashboardArticlesDaggerModule.Binder::class])
internal object DashboardArticlesDaggerModule {

    @Module
    interface Binder {

//        @Binds
//        @DashboardArticlesDaggerScope
//        fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @DashboardArticlesDaggerScope
        @ViewModelKey(DashboardArticlesViewModel::class)
        fun viewModel(viewModel: DashboardArticlesViewModel): ViewModel

    }
}