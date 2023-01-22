package uz.icebergsoft.mobilenews.presentation.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import uz.icebergsoft.mobilenews.R
import uz.icebergsoft.mobilenews.databinding.FragmentSettingsBinding
import uz.icebergsoft.mobilenews.presentation.global.GlobalActivity
import uz.icebergsoft.mobilenews.presentation.presentation.settings.controller.DayNightModeItemController
import uz.icebergsoft.mobilenews.presentation.presentation.settings.di.SettingsDaggerComponent
import uz.icebergsoft.mobilenews.presentation.support.controller.StateEmptyItemController
import uz.icebergsoft.mobilenews.presentation.support.controller.StateErrorItemController
import uz.icebergsoft.mobilenews.presentation.support.controller.StateLoadingItemController
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent.*
import uz.icebergsoft.mobilenews.presentation.utils.addCallback
import uz.icebergsoft.mobilenews.presentation.utils.convertToAppDelegateModeNight
import javax.inject.Inject

internal class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentSettingsBinding

    private val easyAdapter = EasyAdapter()
    private val dayNightModeController = DayNightModeItemController {
        viewModel.saveDayNightMode(it)

        (requireActivity() as GlobalActivity)
            .updateNightMode(it.dayNightMode.convertToAppDelegateModeNight())
    }
    private val stateLoadingController = StateLoadingItemController(isFullScreen = true)
    private val stateEmptyController = StateEmptyItemController(isFullScreen = true)
    private val stateErrorController = StateErrorItemController(isFullScreen = true) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        SettingsDaggerComponent
            .create((requireActivity() as GlobalActivity).globalDaggerComponent)
            .inject(this)

        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) { viewModel.back() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        with(binding) {
            backIv.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
            settingsRv.adapter = easyAdapter
            settingsRv.itemAnimator = null
        }

        subscribeSharedFlows()

        viewModel.getAvailableSettings()
    }

    private fun subscribeSharedFlows() {
        viewModel
            .dayNightModesSharedFlow
            .onEach { event ->
                val itemList = ItemList.create()
                when (event) {
                    is LoadingState -> itemList.add(stateLoadingController)
                    is SuccessState -> itemList.addAll(event.data, dayNightModeController)
                    is EmptyState -> itemList.add(stateEmptyController)
                    is ErrorState -> itemList.add(stateErrorController)
                }
                easyAdapter.setItems(itemList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {

        fun newInstance() =
            SettingsFragment()
    }
}