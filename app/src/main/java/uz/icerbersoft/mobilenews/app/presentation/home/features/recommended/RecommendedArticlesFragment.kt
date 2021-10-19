package uz.icerbersoft.mobilenews.app.presentation.home.features.recommended

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import me.vponomarenko.injectionmanager.IHasComponent
import me.vponomarenko.injectionmanager.x.XInjectionManager
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import uz.icerbersoft.mobilenews.app.R
import uz.icerbersoft.mobilenews.app.databinding.FragmentRecommendedNewsBinding
import uz.icerbersoft.mobilenews.app.presentation.common.model.ArticleWrapper
import uz.icerbersoft.mobilenews.app.presentation.home.features.recommended.controller.RecommendedArticleItemController
import uz.icerbersoft.mobilenews.app.presentation.home.features.recommended.di.RecommendedArticlesDaggerComponent
import uz.icerbersoft.mobilenews.app.support.controller.StateEmptyItemController
import uz.icerbersoft.mobilenews.app.support.controller.StateErrorItemController
import uz.icerbersoft.mobilenews.app.support.controller.StateLoadingItemController
import uz.icerbersoft.mobilenews.app.utils.addCallback
import uz.icerbersoft.mobilenews.app.utils.onBackPressedDispatcher
import javax.inject.Inject

internal class RecommendedArticlesFragment : Fragment(R.layout.fragment_recommended_news),
    IHasComponent<RecommendedArticlesDaggerComponent> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RecommendedArticlesViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentRecommendedNewsBinding

    override fun getComponent(): RecommendedArticlesDaggerComponent =
        RecommendedArticlesDaggerComponent.create(XInjectionManager.findComponent())

    override fun onCreate(savedInstanceState: Bundle?) {
        XInjectionManager.bindComponent(this).inject(this)
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this) { viewModel.back() }
        observeArticleList()
    }

    private val easyAdapter = EasyAdapter()
    private val articleController = RecommendedArticleItemController(
        itemClickListener = { viewModel.openArticleDetailScreen(it.articleId) },
        bookmarkListener = { viewModel.updateBookmark(it) }
    )
    private val stateLoadingController = StateLoadingItemController(true)
    private val stateEmptyItemController = StateEmptyItemController(true)
    private val stateErrorController =
        StateErrorItemController(true) { viewModel.getRecommendedArticles() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecommendedNewsBinding.bind(view)

        with(binding) {
            recommendedArticlesRv.adapter = easyAdapter
            recommendedArticlesRv.itemAnimator = null
        }

        if (savedInstanceState == null)
            viewModel.getRecommendedArticles()
    }

    private fun observeArticleList() {
        viewModel.articlesLiveData.observe(this) { wrappers ->
            val itemList = ItemList.create()
            for (item in wrappers) {
                when (item) {
                    is ArticleWrapper.ArticleItem -> itemList.add(item, articleController)
                    is ArticleWrapper.EmptyItem -> itemList.add(stateEmptyItemController)
                    is ArticleWrapper.ErrorItem -> itemList.add(stateErrorController)
                    is ArticleWrapper.LoadingItem -> itemList.add(stateLoadingController)
                }
            }
            easyAdapter.setItems(itemList)
        }
    }

    companion object {

        fun newInstance() =
            RecommendedArticlesFragment()
    }
}