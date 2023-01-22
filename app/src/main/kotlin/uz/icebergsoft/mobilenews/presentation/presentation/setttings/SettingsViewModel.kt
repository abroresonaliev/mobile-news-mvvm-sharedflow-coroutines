package uz.icebergsoft.mobilenews.presentation.presentation.setttings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.icebergsoft.mobilenews.presentation.presentation.setttings.router.SettingsRouter
import uz.icebergsoft.mobilenews.domain.data.entity.settings.DayNightModeWrapper
import uz.icebergsoft.mobilenews.domain.usecase.daynight.DayNightModeUseCase
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent.*
import uz.icebergsoft.mobilenews.presentation.utils.convertToAppDelegateModeNight
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val useCase: DayNightModeUseCase,
    private val router: SettingsRouter
) : ViewModel() {

    private val dayNightModeWrappers: MutableList<DayNightModeWrapper> = mutableListOf()

    private val _dayNightModesLiveData = MutableLiveData<LoadingListEvent<DayNightModeWrapper>>()
    val dayNightModesLiveData: LiveData<LoadingListEvent<DayNightModeWrapper>> =
        _dayNightModesLiveData

    fun getAvailableSettings() {
        useCase.getDayNightModWrappers()
            .onStart { _dayNightModesLiveData.postValue(LoadingState) }
            .onEach {
                dayNightModeWrappers.clear()
                dayNightModeWrappers.addAll(it)

                if (it.isNotEmpty()) {
                    _dayNightModesLiveData.postValue(SuccessState(it))
                } else {
                    _dayNightModesLiveData.postValue(EmptyState)
                }
            }
            .catch { _dayNightModesLiveData.postValue(ErrorState(it.localizedMessage)) }
            .launchIn(viewModelScope)
    }

    fun saveDayNightMode(dayNightModeWrapper: DayNightModeWrapper) {
        useCase.setDayNightMode(dayNightModeWrapper.dayNightMode.convertToAppDelegateModeNight())

        dayNightModeWrappers.forEach {
            it.isSelected = it.dayNightMode == dayNightModeWrapper.dayNightMode
        }
        _dayNightModesLiveData.postValue(SuccessState(dayNightModeWrappers))
    }

    fun back() = router.back()
}