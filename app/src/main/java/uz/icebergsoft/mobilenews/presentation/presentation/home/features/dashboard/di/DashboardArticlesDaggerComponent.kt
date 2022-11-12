package uz.icebergsoft.mobilenews.presentation.presentation.home.features.dashboard.di

import dagger.Component
import uz.icebergsoft.mobilenews.presentation.global.di.GlobalDaggerComponent
import uz.icebergsoft.mobilenews.presentation.presentation.home.features.dashboard.DashboardArticlesFragment

@DashboardArticlesDaggerScope
@Component(
    dependencies = [GlobalDaggerComponent::class],
    modules = [DashboardArticlesDaggerModule::class]
)
internal interface DashboardArticlesDaggerComponent {

    fun inject(fragment: DashboardArticlesFragment)

    @Component.Factory
    interface Factory {
        fun create(component: GlobalDaggerComponent): DashboardArticlesDaggerComponent
    }

    companion object {
        fun create(component: GlobalDaggerComponent): DashboardArticlesDaggerComponent =
            DaggerDashboardArticlesDaggerComponent
                .factory()
                .create(component)
    }
}