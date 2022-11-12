package uz.icebergsoft.mobilenews.presentation.application.di

import dagger.Module
import dagger.Provides
import uz.icebergsoft.mobilenews.data.datasource.preference.DayNightModePreference
import uz.icebergsoft.mobilenews.presentation.application.manager.daynight.DayNightModeManager
import javax.inject.Singleton

@Module
internal object ApplicationDaggerModuleManager {

    @JvmStatic
    @Provides
    @Singleton
    fun dayNightModeManager(
        dayNightModePreference: DayNightModePreference
    ): DayNightModeManager = DayNightModeManager(dayNightModePreference)
}