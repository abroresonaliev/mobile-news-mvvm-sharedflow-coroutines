package uz.icebergsoft.mobilenews.presentation.global

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import uz.icebergsoft.mobilenews.databinding.ActivityGlobalBinding
import uz.icebergsoft.mobilenews.presentation.application.Application
import uz.icebergsoft.mobilenews.presentation.application.manager.daynight.DayNightModeManager
import uz.icebergsoft.mobilenews.presentation.global.di.GlobalDaggerComponent
import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import javax.inject.Inject

internal class GlobalActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: GlobalViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var cicerone: Cicerone<GlobalRouter>
    private val navigatorHolder: NavigatorHolder by lazy { cicerone.navigatorHolder }
    private val navigator by lazy { SupportAppNavigator(this, binding.frameLayout.id) }

    @Inject
    lateinit var dayNightModeManager: DayNightModeManager

    private val binding by lazy { ActivityGlobalBinding.inflate(layoutInflater) }

    lateinit var globalDaggerComponent: GlobalDaggerComponent
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        GlobalDaggerComponent
            .create((application as Application).applicationDaggerComponent)
            .also { globalDaggerComponent = it }
            .inject(this)

        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(dayNightModeManager.getDayNightMode())
        setContentView(binding.root)
        viewModel.onActivityCreate()
    }

    fun updateNightMode(dayNightMode: Int) {
        dayNightModeManager.setDayNightMode(dayNightMode)
        AppCompatDelegate.setDefaultNightMode(dayNightModeManager.getDayNightMode())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}