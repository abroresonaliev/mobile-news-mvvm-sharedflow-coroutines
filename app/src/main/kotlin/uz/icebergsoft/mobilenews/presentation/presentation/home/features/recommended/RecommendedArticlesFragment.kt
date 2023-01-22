package uz.icebergsoft.mobilenews.presentation.presentation.home.features.recommended

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
import uz.icebergsoft.mobilenews.databinding.FragmentRecommendedNewsBinding
import uz.icebergsoft.mobilenews.presentation.global.GlobalActivity
import uz.icebergsoft.mobilenews.presentation.presentation.home.features.recommended.controller.RecommendedArticleItemController
import uz.icebergsoft.mobilenews.presentation.presentation.home.features.recommended.di.RecommendedArticlesDaggerComponent
import uz.icebergsoft.mobilenews.presentation.support.controller.StateEmptyItemController
import uz.icebergsoft.mobilenews.presentation.support.controller.StateErrorItemController
import uz.icebergsoft.mobilenews.presentation.support.controller.StateLoadingItemController
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent.*
import uz.icebergsoft.mobilenews.presentation.utils.addCallback
import uz.icebergsoft.mobilenews.presentation.utils.onBackPressedDispatcher
import javax.inject.Inject

internal class RecommendedArticlesFragment : Fragment(R.layout.fragment_recommended_news) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RecommendedArticlesViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentRecommendedNewsBinding

    private val easyAdapter = EasyAdapter()
    private val articleController = RecommendedArticleItemController(
        itemClickListener = { viewModel.openArticleDetailScreen(it.articleId) },
        bookmarkListener = { viewModel.updateBookmark(it) }
    )
    private val stateLoadingController = StateLoadingItemController(true)
    private val stateEmptyItemController = StateEmptyItemController(true)
    private val stateErrorController =
        StateErrorItemController(true) { viewModel.getRecommendedArticles() }

    override fun onCreate(savedInstanceState: Bundle?) {
        RecommendedArticlesDaggerComponent
            .create((requireActivity() as GlobalActivity).globalDaggerComponent)
            .inject(this)

        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this) { viewModel.back() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecommendedNewsBinding.bind(view)

        with(binding) {
            recyclerView.adapter = easyAdapter
            recyclerView.itemAnimator = null
        }

        subscribeSharedFlows()
    }

    private fun subscribeSharedFlows() {
        viewModel
            .articlesSharedFlow
            .onEach { state ->
                val itemList = ItemList.create()
                when (state) {
                    is LoadingState -> itemList.add(stateLoadingController)
                    is SuccessState -> itemList.addAll(state.data, articleController)
                    is EmptyState -> itemList.add(stateEmptyItemController)
                    is ErrorState -> itemList.add(stateErrorController)
                }
                easyAdapter.setItems(itemList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {

        fun newInstance() =
            RecommendedArticlesFragment()
    }
}