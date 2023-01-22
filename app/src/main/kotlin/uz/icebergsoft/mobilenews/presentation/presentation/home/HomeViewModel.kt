package uz.icebergsoft.mobilenews.presentation.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import uz.icebergsoft.mobilenews.domain.usecase.home.HomeUseCase
import uz.icebergsoft.mobilenews.presentation.presentation.home.router.HomeRouter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val router: HomeRouter,
    private val useCase: HomeUseCase
) : ViewModel() {

    private val _currentTabSharedFlow = MutableSharedFlow<HomeRouter.HomeTab>()
    val currentTabSharedFlow: SharedFlow<HomeRouter.HomeTab>
        get() = _currentTabSharedFlow.asSharedFlow()

    init {
        router.setNavigationListener { _currentTabSharedFlow.tryEmit(it) }

        useCase
            .isFirstTimeStateFlow
            .onEach { if (it) openDashboardTab(true) }
            .launchIn(viewModelScope)
    }

    fun onFragmentCreated() {
        useCase.onFragmentCreated()
    }

    fun openDashboardTab(isNotifyRequired: Boolean = false) {
        router.openDashboardTab(isNotifyRequired)
    }

    fun openRecommendedTab(isNotifyRequired: Boolean = false) {
        router.openRecommendedTab(isNotifyRequired)
    }

    fun openReadLaterTab(isNotifyRequired: Boolean = false) {
        router.openReadLaterTab(isNotifyRequired)
    }
}