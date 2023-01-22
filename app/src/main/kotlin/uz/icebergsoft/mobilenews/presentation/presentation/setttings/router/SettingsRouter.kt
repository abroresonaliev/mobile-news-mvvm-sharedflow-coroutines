package uz.icebergsoft.mobilenews.presentation.presentation.setttings.router

import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icebergsoft.mobilenews.presentation.support.base.FeatureRouter
import javax.inject.Inject

class SettingsRouter @Inject constructor(
    private val globalRouter: GlobalRouter
) : FeatureRouter() {

    fun back() = globalRouter.exit()
}