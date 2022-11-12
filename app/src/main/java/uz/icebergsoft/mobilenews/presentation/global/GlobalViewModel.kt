package uz.icebergsoft.mobilenews.presentation.global

import androidx.lifecycle.ViewModel
import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import javax.inject.Inject

class GlobalViewModel @Inject constructor(
    private val router: GlobalRouter
) : ViewModel() {

    init {
        router.openHomeScreen()
    }

    fun onActivityCreate() {

    }
}