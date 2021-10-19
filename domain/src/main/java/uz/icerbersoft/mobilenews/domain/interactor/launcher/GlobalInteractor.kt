package uz.icerbersoft.mobilenews.domain.interactor.launcher

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.icerbersoft.mobilenews.domain.interactor.launcher.state.LauncherState
import javax.inject.Inject

class GlobalInteractor @Inject constructor() {

    private fun isFirstTime(): Boolean =
        false

    private fun isLanguageSelected(): Boolean =
        true

    private fun isLoginHasBeen(): Boolean =
        true

    fun getCurrentLauncherState(): Flow<LauncherState> =
        flow {
            val state = when {
                isFirstTime() -> LauncherState.FIRST_TIME_LAUNCH
                !isLanguageSelected() -> LauncherState.LANGUAGE_NOT_SELECTED
                else -> LauncherState.DEFAULT
            }
            emit(state)
        }
}