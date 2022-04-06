package uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended.di

import dagger.Subcomponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended.RecommendedArticlesFragment

@RecommendedArticlesDaggerScope
@Subcomponent(modules = [RecommendedArticlesDaggerModule::class])
internal interface RecommendedArticlesDaggerComponent {

    fun inject(fragment: RecommendedArticlesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): RecommendedArticlesDaggerComponent
    }
}