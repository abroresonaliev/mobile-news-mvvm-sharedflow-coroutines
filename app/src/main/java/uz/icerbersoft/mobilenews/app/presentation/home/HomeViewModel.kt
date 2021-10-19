package uz.icerbersoft.mobilenews.app.presentation.home

import androidx.lifecycle.ViewModel
import uz.icerbersoft.mobilenews.app.presentation.home.router.HomeRouter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val router: HomeRouter
) : ViewModel() {

    fun openDashboardTab() {
        router.openDashboardTab()
    }

    fun openRecommendedTab() {
        router.openRecommendedTab()
    }

    fun openReadLaterTab() {
        router.openReadLaterTab()
    }
}