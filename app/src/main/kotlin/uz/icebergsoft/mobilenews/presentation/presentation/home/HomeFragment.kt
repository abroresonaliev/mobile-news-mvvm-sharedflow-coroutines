package uz.icebergsoft.mobilenews.presentation.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import uz.icebergsoft.mobilenews.R
import uz.icebergsoft.mobilenews.databinding.FragmentHomeBinding
import uz.icebergsoft.mobilenews.presentation.global.GlobalActivity
import uz.icebergsoft.mobilenews.presentation.presentation.home.di.HomeDaggerComponent
import uz.icebergsoft.mobilenews.presentation.presentation.home.router.HomeRouter.HomeTab
import javax.inject.Inject

internal class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private var supportAppNavigator: SupportAppNavigator? = null

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        HomeDaggerComponent
            .create((requireActivity() as GlobalActivity).globalDaggerComponent)
            .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        supportAppNavigator =
            SupportAppNavigator(requireActivity(), childFragmentManager, binding.frameLayout.id)

        binding.apply {
            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home_dashboard -> viewModel.openDashboardTab().let { true }
                    R.id.home_recommended_news -> viewModel.openRecommendedTab().let { true }
                    R.id.home_read_later_news -> viewModel.openReadLaterTab().let { true }
                    else -> false
                }
            }
            bottomNavigationView.setOnItemReselectedListener {
                when (it.itemId) {
                    R.id.home_dashboard -> viewModel.openDashboardTab()
                    R.id.home_recommended_news -> viewModel.openRecommendedTab()
                    R.id.home_read_later_news -> viewModel.openReadLaterTab()
                }
            }
        }

        viewModel.onFragmentCreated()

        subscribeSharedFlows()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.removeNavigator()
        supportAppNavigator?.let { navigatorHolder.setNavigator(it) }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("is_first_time", false)
    }

    override fun onDestroyView() {
        supportAppNavigator = null
        super.onDestroyView()
    }

    private fun subscribeSharedFlows() {
        viewModel
            .currentTabSharedFlow
            .onEach {
                binding.bottomNavigationView.selectedItemId = when (it) {
                    HomeTab.DashboardTab -> R.id.home_dashboard
                    HomeTab.RecommendedTab -> R.id.home_recommended_news
                    HomeTab.ReadLaterTab -> R.id.home_read_later_news
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {

        fun newInstance() = HomeFragment()
    }
}