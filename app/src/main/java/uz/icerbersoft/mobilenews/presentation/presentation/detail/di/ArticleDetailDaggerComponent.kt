package uz.icerbersoft.mobilenews.presentation.presentation.detail.di

import dagger.Subcomponent
import uz.icerbersoft.mobilenews.presentation.presentation.detail.ArticleDetailFragment

@ArticleDetailDaggerScope
@Subcomponent(modules = [ArticleDetailDaggerModule::class])
internal interface ArticleDetailDaggerComponent {

    fun inject(fragment: ArticleDetailFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ArticleDetailDaggerComponent
    }
}