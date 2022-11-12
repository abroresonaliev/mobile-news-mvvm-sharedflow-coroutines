package uz.icebergsoft.mobilenews.presentation.presentation.home.features.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCase
import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent.*
import javax.inject.Inject

internal class DashboardArticlesViewModel @Inject constructor(
    private val useCase: DashboardArticlesUseCase,
    private val router: GlobalRouter
) : ViewModel() {

    private val _breakingArticlesLiveData = MutableLiveData<LoadingListEvent<Article>>()
    val breakingArticlesLiveData: LiveData<LoadingListEvent<Article>> = _breakingArticlesLiveData

    private val _topArticlesLiveData = MutableLiveData<LoadingListEvent<Article>>()
    val topArticlesLiveData: LiveData<LoadingListEvent<Article>> = _topArticlesLiveData

    fun getBreakingArticles() {
        useCase.getBreakingArticles()
            .onStart { _breakingArticlesLiveData.value = LoadingState }
            .catch { _breakingArticlesLiveData.value = ErrorState(it.message) }
            .onEach {
                _breakingArticlesLiveData.value =
                    if (it.articles.isNotEmpty()) SuccessState(it.articles)
                    else EmptyState
            }
            .launchIn(viewModelScope)
    }

    fun getTopArticles() {
        flowOf(Unit)
            .debounce(1500)
            .flatMapConcat {
                useCase.getTopArticles()
                    .onStart { _topArticlesLiveData.value = LoadingState }
                    .catch { _topArticlesLiveData.value = ErrorState(it.message) }
                    .onEach {
                        _topArticlesLiveData.value =
                            if (it.articles.isNotEmpty()) SuccessState(it.articles)
                            else EmptyState
                    }
            }
            .launchIn(viewModelScope)
    }

    fun updateBookmark(article: Article) {
        useCase
            .updateBookmark(article)
            .launchIn(viewModelScope)
    }

    fun openArticleDetailScreen(article: Article) =
        router.openArticleDetailScreen(article.articleId)

    fun openSettingsScreen() =
        router.openSettingsScreen()
}