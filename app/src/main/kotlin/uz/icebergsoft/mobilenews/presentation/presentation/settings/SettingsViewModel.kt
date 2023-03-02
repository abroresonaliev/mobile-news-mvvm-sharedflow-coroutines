package uz.icebergsoft.mobilenews.presentation.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import uz.icebergsoft.mobilenews.domain.data.model.settings.DayNightModeWrapper
import uz.icebergsoft.mobilenews.domain.usecase.daynight.DayNightModeUseCase
import uz.icebergsoft.mobilenews.presentation.presentation.settings.router.SettingsRouter
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent.*
import uz.icebergsoft.mobilenews.presentation.utils.convertToAppDelegateModeNight
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val useCase: DayNightModeUseCase,
    private val router: SettingsRouter
) : ViewModel() {

    private val dayNightModeWrappers: MutableList<DayNightModeWrapper> = mutableListOf()

    private val _dayNightModesSharedFlow =
        MutableSharedFlow<LoadingListEvent<DayNightModeWrapper>>()
    val dayNightModesSharedFlow: SharedFlow<LoadingListEvent<DayNightModeWrapper>>
        get() = _dayNightModesSharedFlow.asSharedFlow()

    fun getAvailableSettings() {
        useCase.getDayNightModWrappers()
            .onStart { _dayNightModesSharedFlow.emit(LoadingState) }
            .onEach {
                dayNightModeWrappers.clear()
                dayNightModeWrappers.addAll(it)

                if (it.isNotEmpty()) {
                    _dayNightModesSharedFlow.emit(SuccessState(it))
                } else {
                    _dayNightModesSharedFlow.emit(EmptyState)
                }
            }
            .catch { _dayNightModesSharedFlow.emit(ErrorState(it.localizedMessage)) }
            .launchIn(viewModelScope)
    }

    fun saveDayNightMode(dayNightModeWrapper: DayNightModeWrapper) {
        useCase.setDayNightMode(dayNightModeWrapper.dayNightMode.convertToAppDelegateModeNight())

        dayNightModeWrappers.forEach {
            it.isSelected = it.dayNightMode == dayNightModeWrapper.dayNightMode
        }
        _dayNightModesSharedFlow.tryEmit(SuccessState(dayNightModeWrappers))
    }

    fun back() = router.back()
}