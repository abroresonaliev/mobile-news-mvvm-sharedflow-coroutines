package uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater.di

import dagger.Subcomponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater.ReadLaterArticlesFragment

@ReadLaterArticlesDaggerScope
@Subcomponent(modules = [ReadLaterArticlesDaggerModule::class])
internal interface ReadLaterArticlesDaggerComponent {

    fun inject(fragment: ReadLaterArticlesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ReadLaterArticlesDaggerComponent
    }
}