package uz.icebergsoft.mobilenews.domain.usecase.global

import kotlinx.coroutines.flow.*

class GlobalUseCaseImpl : GlobalUseCase {

    private val _isFirstTimeStateFlow = MutableStateFlow(true)
    override val isFirstTimeStateFlow: StateFlow<Boolean>
        get() = _isFirstTimeStateFlow.asStateFlow()

    override fun onActivityCreated() {
        _isFirstTimeStateFlow.tryEmit(false)
    }
}