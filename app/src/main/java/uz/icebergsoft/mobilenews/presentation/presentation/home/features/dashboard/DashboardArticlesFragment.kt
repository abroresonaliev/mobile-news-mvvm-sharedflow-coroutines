package uz.icebergsoft.mobilenews.presentation.presentation.home.features.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import uz.icebergsoft.mobilenews.R
import uz.icebergsoft.mobilenews.data.utils.date.toFormattedDate
import uz.icebergsoft.mobilenews.databinding.FragmentDashboardArticlesBinding
import uz.icebergsoft.mobilenews.presentation.global.GlobalActivity
import uz.icebergsoft.mobilenews.presentation.presentation.home.features.dashboard.controller.BreakingArticleItemController
import uz.icebergsoft.mobilenews.presentation.presentation.home.features.dashboard.controller.TopArticleItemController
import uz.icebergsoft.mobilenews.presentation.presentation.home.features.dashboard.di.DashboardArticlesDaggerComponent
import uz.icebergsoft.mobilenews.presentation.support.controller.StateEmptyItemController
import uz.icebergsoft.mobilenews.presentation.support.controller.StateErrorItemController
import uz.icebergsoft.mobilenews.presentation.support.controller.StateLoadingItemController
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent.*
import uz.icebergsoft.mobilenews.presentation.utils.addCallback
import uz.icebergsoft.mobilenews.presentation.utils.onBackPressedDispatcher
import java.util.*
import javax.inject.Inject

internal class DashboardArticlesFragment : Fragment(R.layout.fragment_dashboard_articles) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DashboardArticlesViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentDashboardArticlesBinding

    private val breakingArticlesAdapter = EasyAdapter()
    private val breakingArticleController = BreakingArticleItemController(
        itemClickListener = { viewModel.openArticleDetailScreen(it) },
        bookmarkListener = { viewModel.updateBookmark(it) }
    )
    private val breakingLoadingController = StateLoadingItemController(true)
    private val breakingEmptyController = StateEmptyItemController(true)
    private val breakingErrorController =
        StateErrorItemController(true) { viewModel.getBreakingArticles() }

    private val topArticlesAdapter = EasyAdapter()
    private val topArticleController = TopArticleItemController(
        itemClickListener = { viewModel.openArticleDetailScreen(it) },
        bookmarkListener = { viewModel.updateBookmark(it) }
    )
    private val topLoadingController = StateLoadingItemController(true)
    private val topEmptyController = StateEmptyItemController(true)
    private val topErrorController =
        StateErrorItemController(true) { viewModel.getTopArticles() }

    override fun onCreate(savedInstanceState: Bundle?) {
        DashboardArticlesDaggerComponent
            .create((requireActivity() as GlobalActivity).globalDaggerComponent)
            .inject(this)

        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this) { requireActivity().finish() }

        observeLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardArticlesBinding.bind(view)

        with(binding) {
            todayDateTv.text = Date().toFormattedDate("EEEE, dd MMMM")
            breakingArticleRv.adapter = breakingArticlesAdapter
            breakingArticleRv.itemAnimator = null
            topArticleRv.adapter = topArticlesAdapter
            topArticleRv.itemAnimator = null
            settingsIv.setOnClickListener { viewModel.openSettingsScreen() }
        }
        if (savedInstanceState == null) {
            with(viewModel) {
                getBreakingArticles()
                getTopArticles()
            }
        }
    }

    private fun observeLiveData() {
        with(viewModel) {
            breakingArticlesLiveData.observe(this@DashboardArticlesFragment) { state ->
                val itemList = ItemList.create()
                when (state) {
                    is LoadingState -> itemList.add(breakingLoadingController)
                    is SuccessState -> itemList.addAll(state.data, breakingArticleController)
                    is EmptyState -> itemList.add(breakingEmptyController)
                    is ErrorState -> itemList.add(breakingErrorController)
                }
                breakingArticlesAdapter.setItems(itemList)
            }

            topArticlesLiveData.observe(this@DashboardArticlesFragment) { state ->
                val itemList = ItemList.create()
                when (state) {
                    is SuccessState -> itemList.addAll(state.data, topArticleController)
                    is EmptyState -> itemList.add(topEmptyController)
                    is ErrorState -> itemList.add(topErrorController)
                    is LoadingState -> itemList.add(topLoadingController)
                }
                topArticlesAdapter.setItems(itemList)
            }
        }
    }

    companion object {

        fun newInstance() =
            DashboardArticlesFragment()
    }
}