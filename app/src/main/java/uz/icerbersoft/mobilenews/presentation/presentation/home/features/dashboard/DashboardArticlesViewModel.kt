package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.presentation.utils.LoadingState
import uz.icerbersoft.mobilenews.presentation.utils.LoadingState.*
import uz.icerbersoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCase
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import javax.inject.Inject

internal class DashboardArticlesViewModel @Inject constructor(
    private val useCase: DashboardArticlesUseCase,
    private val router: GlobalRouter
) : ViewModel() {

    private val _breakingArticlesLiveData = MutableLiveData<LoadingState<List<Article>>>()
    val breakingArticlesLiveData: LiveData<LoadingState<List<Article>>> = _breakingArticlesLiveData

    private val _topArticlesLiveData = MutableLiveData<LoadingState<List<Article>>>()
    val topArticlesLiveData: LiveData<LoadingState<List<Article>>> = _topArticlesLiveData

    fun getBreakingArticles() {
        useCase.getBreakingArticles()
            .onStart { _breakingArticlesLiveData.value = LoadingItem }
            .catch { _breakingArticlesLiveData.value = ErrorItem }
            .onEach {
                _breakingArticlesLiveData.value =
                    if (it.articles.isNotEmpty()) SuccessItem(it.articles)
                    else EmptyItem
            }
            .launchIn(viewModelScope)
    }

    fun getTopArticles() {
        flowOf(Unit)
            .debounce(1500)
            .flatMapConcat {
                useCase.getTopArticles()
                    .onStart { _topArticlesLiveData.value = LoadingItem }
                    .catch { _topArticlesLiveData.value = ErrorItem }
                    .onEach {
                        _topArticlesLiveData.value =
                            if (it.articles.isNotEmpty()) SuccessItem(it.articles)
                            else EmptyItem
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
}