package uz.icebergsoft.mobilenews.presentation.application.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import uz.icebergsoft.mobilenews.presentation.application.Application
import uz.icebergsoft.mobilenews.presentation.application.di.data.DataDaggerModuleDataSource
import uz.icebergsoft.mobilenews.presentation.application.di.data.DataDaggerModulePreference
import uz.icebergsoft.mobilenews.presentation.application.di.data.DataDaggerModuleRepository
import uz.icebergsoft.mobilenews.presentation.application.di.domain.DomainDaggerModuleUseCase
import uz.icebergsoft.mobilenews.presentation.application.di.domain.DomainUseCaseProvider
import uz.icebergsoft.mobilenews.presentation.application.manager.daynight.DayNightModeManager
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationDaggerModule::class,
        ApplicationDaggerModuleManager::class,
        DataDaggerModuleDataSource::class,
        DataDaggerModulePreference::class,
        DataDaggerModuleRepository::class,
        DomainDaggerModuleUseCase::class
    ]
)
internal interface ApplicationDaggerComponent : DomainUseCaseProvider {

    val dayNightModeManager: DayNightModeManager

    fun inject(application: Application)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationDaggerComponent
    }

    companion object {
        fun create(context: Context): ApplicationDaggerComponent =
            DaggerApplicationDaggerComponent
                .factory()
                .create(context)
    }
}