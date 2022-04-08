package uz.icerbersoft.mobilenews.presentation.global.di

import dagger.Component
import uz.icerbersoft.mobilenews.presentation.application.di.ApplicationDaggerComponent
import uz.icerbersoft.mobilenews.presentation.application.di.domain.DomainUseCaseProvider
import uz.icerbersoft.mobilenews.presentation.global.GlobalActivity
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter

@GlobalScope
@Component(
    dependencies = [ApplicationDaggerComponent::class],
    modules = [
        GlobalDaggerModule::class,
        GlobalDaggerModuleNavigation::class
    ]
)
internal interface GlobalDaggerComponent : DomainUseCaseProvider {

    val globalRouter: GlobalRouter

    val homeRouter: HomeRouter

    fun inject(activity: GlobalActivity)

    @Component.Factory
    interface Factory {
        fun create(component: ApplicationDaggerComponent): GlobalDaggerComponent
    }

    companion object {
        fun create(component: ApplicationDaggerComponent): GlobalDaggerComponent =
            DaggerGlobalDaggerComponent
                .factory()
                .create(component)
    }
}