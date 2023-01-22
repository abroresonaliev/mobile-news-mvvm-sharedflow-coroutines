package uz.icebergsoft.mobilenews.domain.usecase.global

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface GlobalUseCase {

    val isFirstTimeStateFlow: StateFlow<Boolean>

    fun onActivityCreated()
}