package uz.icerbersoft.mobilenews.presentation.global

import androidx.lifecycle.ViewModel
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import javax.inject.Inject

class GlobalViewModel @Inject constructor(
    private val router: GlobalRouter
) : ViewModel() {

    fun onActivityCreate() {
        router.openHomeScreen()
    }
}