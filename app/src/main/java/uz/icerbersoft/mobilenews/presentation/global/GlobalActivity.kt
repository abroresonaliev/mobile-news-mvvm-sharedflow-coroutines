package uz.icerbersoft.mobilenews.presentation.global

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import uz.icerbersoft.mobilenews.databinding.ActivityGlobalBinding
import uz.icerbersoft.mobilenews.presentation.application.Application
import uz.icerbersoft.mobilenews.presentation.global.di.GlobalDaggerComponent
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import javax.inject.Inject

internal class GlobalActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: GlobalViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var cicerone: Cicerone<GlobalRouter>
    private val navigatorHolder: NavigatorHolder by lazy { cicerone.navigatorHolder }
    private val navigator by lazy { SupportAppNavigator(this, binding.frameLayout.id) }

    private val binding by lazy { ActivityGlobalBinding.inflate(layoutInflater) }

    lateinit var globalDaggerComponent: GlobalDaggerComponent
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        GlobalDaggerComponent
            .create((application as Application).applicationDaggerComponent)
            .also { globalDaggerComponent = it }
            .inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.onActivityCreate()
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