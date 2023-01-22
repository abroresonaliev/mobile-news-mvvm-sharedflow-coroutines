package uz.icebergsoft.mobilenews.presentation.presentation.home.di

import dagger.Component
import uz.icebergsoft.mobilenews.presentation.global.di.GlobalDaggerComponent
import uz.icebergsoft.mobilenews.presentation.presentation.home.HomeFragment

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