package uz.icebergsoft.mobilenews.presentation.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.icebergsoft.mobilenews.domain.usecase.global.GlobalUseCase
import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import javax.inject.Inject

class GlobalViewModel @Inject constructor(
    private val router: GlobalRouter,
    private val useCase: GlobalUseCase
) : ViewModel() {

    init {
        useCase
            .isFirstTimeStateFlow
            .onEach { if (it) router.openHomeScreen() }
            .launchIn(viewModelScope)
    }

    fun onActivityCreated() {
        useCase.onActivityCreated()
    }
}