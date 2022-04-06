package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.di

import dagger.Subcomponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.DashboardArticlesFragment

@DashboardArticlesDaggerScope
@Subcomponent(modules = [DashboardArticlesDaggerModule::class])
internal interface DashboardArticlesDaggerComponent {

    fun inject(fragment: DashboardArticlesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardArticlesDaggerComponent
    }
}