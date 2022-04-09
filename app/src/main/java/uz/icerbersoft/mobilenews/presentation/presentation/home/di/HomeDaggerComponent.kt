package uz.icerbersoft.mobilenews.presentation.presentation.home.di

import dagger.Component
import uz.icerbersoft.mobilenews.presentation.global.di.GlobalDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.HomeFragment

@HomeDaggerScope
@Component(
    dependencies = [GlobalDaggerComponent::class],
    modules = [HomeDaggerModule::class]
)
internal interface HomeDaggerComponent {

    fun inject(fragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(component: GlobalDaggerComponent): HomeDaggerComponent
    }

    companion object {
        fun create(component: GlobalDaggerComponent): HomeDaggerComponent =
            DaggerHomeDaggerComponent
                .factory()
                .create(component)
    }
}