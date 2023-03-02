package uz.icebergsoft.mobilenews.presentation.presentation.home.features.recommended

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import uz.icebergsoft.mobilenews.domain.data.model.article.Article
import uz.icebergsoft.mobilenews.domain.usecase.article.recommended.RecommendedArticlesUseCase
import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icebergsoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import javax.inject.Inject

internal class RecommendedArticlesViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter,
    private val useCase: RecommendedArticlesUseCase,
) : ViewModel() {

    val articlesSharedFlow: SharedFlow<LoadingListEvent<Article>>
        get() = useCase.articlesSharedFlow

    init {
        getRecommendedArticles()
    }

    fun getRecommendedArticles() {
        useCase
            .getRecommendedArticles()
            .launchIn(viewModelScope)
    }

    fun updateBookmark(article: Article) {
        useCase
            .updateBookmark(article)
            .launchIn(viewModelScope)
    }

    fun openArticleDetailScreen(articleId: String) =
        globalRouter.openArticleDetailScreen(articleId)

    fun back() =
        homeRouter.openDashboardTab(true)
}