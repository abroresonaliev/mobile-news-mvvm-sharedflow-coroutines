package uz.icerbersoft.mobilenews.app.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.icerbersoft.mobilenews.app.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.domain.interactor.launcher.GlobalInteractor
import javax.inject.Inject

class GlobalViewModel @Inject constructor(
    private val interactor: GlobalInteractor,
    private val router: GlobalRouter
) : ViewModel() {

    fun onActivityCreate() {
        interactor.getCurrentLauncherState()
            .onEach {
                when (it) {
                    else -> router.openHomeScreen()
                }
            }
            .launchIn(viewModelScope)
    }
}