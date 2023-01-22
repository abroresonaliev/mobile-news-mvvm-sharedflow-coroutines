package uz.icebergsoft.mobilenews.presentation.presentation.home.features.readlater.di

import dagger.Component
import uz.icebergsoft.mobilenews.presentation.global.di.GlobalDaggerComponent
import uz.icebergsoft.mobilenews.presentation.presentation.home.features.readlater.ReadLaterArticlesFragment

@ReadLaterArticlesDaggerScope
@Component(
    dependencies = [GlobalDaggerComponent::class],
    modules = [ReadLaterArticlesDaggerModule::class]
)
internal interface ReadLaterArticlesDaggerComponent {

    fun inject(fragment: ReadLaterArticlesFragment)

    @Component.Factory
    interface Factory {
        fun create(component: GlobalDaggerComponent): ReadLaterArticlesDaggerComponent
    }

    companion object {
        fun create(component: GlobalDaggerComponent): ReadLaterArticlesDaggerComponent =
            DaggerReadLaterArticlesDaggerComponent
                .factory()
                .create(component)
    }
}