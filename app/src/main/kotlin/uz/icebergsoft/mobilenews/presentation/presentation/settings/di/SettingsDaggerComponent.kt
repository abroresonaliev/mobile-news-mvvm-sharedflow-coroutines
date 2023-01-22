package uz.icebergsoft.mobilenews.presentation.presentation.settings.di

import dagger.Component
import uz.icebergsoft.mobilenews.presentation.global.di.GlobalDaggerComponent
import uz.icebergsoft.mobilenews.presentation.presentation.settings.SettingsFragment

@SettingsDaggerScope
@Component(
    dependencies = [GlobalDaggerComponent::class],
    modules = [SettingsDaggerModule::class]
)
internal interface SettingsDaggerComponent {

    fun inject(fragment: SettingsFragment)

    @Component.Factory
    interface Factory {
        fun create(component: GlobalDaggerComponent): SettingsDaggerComponent
    }

    companion object {
        fun create(component: GlobalDaggerComponent): SettingsDaggerComponent =
            DaggerSettingsDaggerComponent.factory()
                .create(component)
    }
}