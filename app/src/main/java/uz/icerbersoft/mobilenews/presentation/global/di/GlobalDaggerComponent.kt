package uz.icerbersoft.mobilenews.presentation.global.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import uz.icerbersoft.mobilenews.presentation.global.GlobalActivity
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.detail.di.ArticleDetailDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.di.HomeDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.di.DashboardArticlesDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater.di.ReadLaterArticlesDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended.di.RecommendedArticlesDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter

@GlobalScope
@Subcomponent(
    modules = [
        GlobalDaggerModule::class,
        GlobalDaggerModuleNavigation::class,
        GlobalDaggerModuleSubComponents::class
    ]
)
internal interface GlobalDaggerComponent {

    val globalRouter: GlobalRouter
    val homeRouter: HomeRouter

    val viewModelFactory: ViewModelProvider.Factory

    val articleDetailDaggerComponent: ArticleDetailDaggerComponent.Factory
    val dashboardArticlesDaggerComponent: DashboardArticlesDaggerComponent.Factory
    val homeDaggerComponent: HomeDaggerComponent.Factory
    val readLaterArticlesDaggerComponent: ReadLaterArticlesDaggerComponent.Factory
    val recommendedArticlesDaggerComponent: RecommendedArticlesDaggerComponent.Factory

    fun inject(activity: GlobalActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): GlobalDaggerComponent
    }
}