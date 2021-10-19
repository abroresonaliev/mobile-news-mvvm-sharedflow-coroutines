package uz.icerbersoft.mobilenews.app.presentation.home.features.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import uz.icerbersoft.mobilenews.app.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.app.presentation.common.model.ArticleWrapper
import uz.icerbersoft.mobilenews.app.presentation.common.model.ArticleWrapper.*
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.interactor.article.list.ArticleListInteractor
import javax.inject.Inject

internal class DashboardArticlesViewModel @Inject constructor(
    private val interactor: ArticleListInteractor,
    private val router: GlobalRouter
) : ViewModel() {

    private val _breakingArticlesLiveData = MutableLiveData<List<ArticleWrapper>>()

    val breakingArticlesLiveData: LiveData<List<ArticleWrapper>>
        get() = _breakingArticlesLiveData

    private val _topArticlesLiveData = MutableLiveData<List<ArticleWrapper>>()

    val topArticlesLiveData: LiveData<List<ArticleWrapper>>
        get() = _topArticlesLiveData

    fun getBreakingArticles() {
        interactor.getBreakingArticles()
            .onStart { _breakingArticlesLiveData.value = listOf(LoadingItem) }
            .catch { _breakingArticlesLiveData.value = listOf(ErrorItem) }
            .onEach { it ->
                _breakingArticlesLiveData.value =
                    if (it.articles.isNotEmpty()) it.articles.map { ArticleItem(it) }
                    else listOf(EmptyItem)
            }
            .launchIn(viewModelScope)
    }

    fun getTopArticles() {
        flowOf(Unit)
            .debounce(1500)
            .flatMapConcat {
                interactor.getTopArticles()
                    .onStart { _topArticlesLiveData.value = listOf(LoadingItem) }
                    .catch { _topArticlesLiveData.value = listOf(ErrorItem) }
                    .onEach { it ->
                        _topArticlesLiveData.value =
                            if (it.articles.isNotEmpty()) it.articles.map { ArticleItem(it) }
                            else listOf(EmptyItem)
                    }
            }
            .launchIn(viewModelScope)
    }

    fun updateBookmark(article: Article) {
        interactor
            .updateBookmark(article)
            .launchIn(viewModelScope)
    }

    fun openArticleDetailScreen(article: Article) =
        router.openArticleDetailScreen(article.articleId)
}