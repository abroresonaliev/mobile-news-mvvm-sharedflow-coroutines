package uz.icebergsoft.mobilenews.domain.data.repository.settings

import kotlinx.coroutines.flow.Flow
import uz.icebergsoft.mobilenews.domain.data.model.settings.DayNightMode

interface SettingsRepository {

    fun getSelectedDayNightMode(): Flow<DayNightMode>

    fun getDayNightModes(): Flow<List<DayNightMode>>

    fun saveDayNightMode(value: DayNightMode)
}