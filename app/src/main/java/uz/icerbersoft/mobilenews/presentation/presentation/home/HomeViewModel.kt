package uz.icerbersoft.mobilenews.presentation.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val router: HomeRouter
) : ViewModel() {

    private val _currentTabMutableLiveData = MutableLiveData<HomeRouter.HomeTab>()
    val currentTabMutableLiveData: LiveData<HomeRouter.HomeTab> = _currentTabMutableLiveData

    init {
        router.setNavigationListener { _currentTabMutableLiveData.postValue(it) }
    }

    fun openDashboardTab(isNotifyRequired: Boolean = false) {
        router.openDashboardTab(isNotifyRequired)
    }

    fun openRecommendedTab(isNotifyRequired: Boolean = false) {
        router.openRecommendedTab(isNotifyRequired)
    }

    fun openReadLaterTab(isNotifyRequired: Boolean = false) {
        router.openReadLaterTab(isNotifyRequired)
    }
}