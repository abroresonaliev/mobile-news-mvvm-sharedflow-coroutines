package uz.icebergsoft.mobilenews.presentation.presentation.home.features.readlater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import uz.icebergsoft.mobilenews.domain.data.model.article.Article
import uz.icebergsoft.mobilenews.domain.usecase.article.readlater.ReadLaterArticlesUseCase
import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icebergsoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import javax.inject.Inject

internal class ReadLaterArticlesViewModel @Inject constructor(
    private val homeRouter: HomeRouter,
    private val globalRouter: GlobalRouter,
    private val useCase: ReadLaterArticlesUseCase
) : ViewModel() {

    val articlesSharedFlow: SharedFlow<LoadingListEvent<Article>>
        get() = useCase.articlesSharedFlow

    init {
        getReadLaterArticles()
    }

    fun getReadLaterArticles() {
        useCase
            .getReadLaterArticles()
            .launchIn(viewModelScope)
    }

    fun openArticleDetailScreen(articleId: String) =
        globalRouter.openArticleDetailScreen(articleId)

    fun back() =
        homeRouter.openDashboardTab(true)
}