package uz.icebergsoft.mobilenews.domain.usecase.home

import kotlinx.coroutines.flow.StateFlow

interface HomeUseCase {

    val isFirstTimeStateFlow: StateFlow<Boolean>

    fun onFragmentCreated()
}