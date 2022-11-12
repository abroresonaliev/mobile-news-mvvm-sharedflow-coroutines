package uz.icebergsoft.mobilenews.presentation.presentation.home.features.recommended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.domain.usecase.article.recommended.RecommendedArticlesUseCase
import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icebergsoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent.*
import javax.inject.Inject

internal class RecommendedArticlesViewModel @Inject constructor(
    private val useCase: RecommendedArticlesUseCase,
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter,
) : ViewModel() {

    private val _articlesLiveData = MutableLiveData<LoadingListEvent<Article>>()
    val articlesLiveData: LiveData<LoadingListEvent<Article>> = _articlesLiveData

    fun getRecommendedArticles() {
        useCase
            .getRecommendedArticles()
            .onStart { _articlesLiveData.value = LoadingState }
            .catch { _articlesLiveData.value = ErrorState(it.message) }
            .onEach {
                _articlesLiveData.value =
                    if (it.articles.isNotEmpty()) SuccessState(it.articles)
                    else EmptyState
            }
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