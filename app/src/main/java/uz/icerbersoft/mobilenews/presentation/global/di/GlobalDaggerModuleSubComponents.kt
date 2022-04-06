package uz.icerbersoft.mobilenews.presentation.global.di

import dagger.Module
import uz.icerbersoft.mobilenews.presentation.presentation.detail.di.ArticleDetailDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.di.HomeDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.di.DashboardArticlesDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater.di.ReadLaterArticlesDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended.di.RecommendedArticlesDaggerComponent

@Module(
    subcomponents = [
        ArticleDetailDaggerComponent::class,
        DashboardArticlesDaggerComponent::class,
        HomeDaggerComponent::class,
        ReadLaterArticlesDaggerComponent::class,
        RecommendedArticlesDaggerComponent::class
    ]
)
class GlobalDaggerModuleSubComponents