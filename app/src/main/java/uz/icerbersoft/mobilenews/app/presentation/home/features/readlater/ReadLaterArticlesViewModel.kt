package uz.icerbersoft.mobilenews.app.presentation.home.features.readlater

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.icerbersoft.mobilenews.app.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.app.presentation.common.model.ArticleWrapper
import uz.icerbersoft.mobilenews.app.presentation.common.model.ArticleWrapper.*
import uz.icerbersoft.mobilenews.app.presentation.home.router.HomeRouter
import uz.icerbersoft.mobilenews.domain.interactor.article.list.ArticleListInteractor
import javax.inject.Inject

internal class ReadLaterArticlesViewModel @Inject constructor(
    private val interactor: ArticleListInteractor,
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter
) : ViewModel() {

    private val _articlesLiveData = MutableLiveData<List<ArticleWrapper>>()

    val articlesLiveData: LiveData<List<ArticleWrapper>>
        get() = _articlesLiveData

    fun getReadLaterArticles() {
        interactor
            .getReadLaterArticles()
            .onStart { _articlesLiveData.value = listOf(LoadingItem) }
            .catch { _articlesLiveData.value = listOf(EmptyItem) }
            .onEach {
                _articlesLiveData.value =
                    if (it.articles.isNotEmpty()) it.articles.map { ArticleItem(it) }
                    else (listOf(EmptyItem))
            }
            .launchIn(viewModelScope)
    }

    fun openArticleDetailScreen(articleId: String) =
        globalRouter.openArticleDetailScreen(articleId)

    fun back() =
        homeRouter.openDashboardTab(true)
}