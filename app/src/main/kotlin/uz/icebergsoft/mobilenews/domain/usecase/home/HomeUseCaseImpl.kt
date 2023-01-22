package uz.icebergsoft.mobilenews.domain.usecase.home

import kotlinx.coroutines.flow.*

class HomeUseCaseImpl : HomeUseCase {

    private val _isFirstTimeStateFlow = MutableStateFlow(true)
    override val isFirstTimeStateFlow: StateFlow<Boolean>
        get() = _isFirstTimeStateFlow.asStateFlow()

    override fun onFragmentCreated() {
        _isFirstTimeStateFlow.tryEmit(false)
    }
}