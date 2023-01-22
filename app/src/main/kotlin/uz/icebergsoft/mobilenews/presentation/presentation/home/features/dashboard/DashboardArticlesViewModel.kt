package uz.icebergsoft.mobilenews.presentation.presentation.home.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCase
import uz.icebergsoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import javax.inject.Inject

internal class DashboardArticlesViewModel @Inject constructor(
    private val useCase: DashboardArticlesUseCase,
    private val router: GlobalRouter
) : ViewModel() {

    val breakingArticlesSharedFlow: SharedFlow<LoadingListEvent<Article>>
        get() = useCase.breakingArticlesSharedFlow

    val topArticlesSharedFlow: SharedFlow<LoadingListEvent<Article>>
        get() = useCase.topArticlesSharedFlow

    init {
        getBreakingArticles()
        getTopArticles()
    }

    fun getBreakingArticles() {
        useCase.getBreakingArticles()
            .launchIn(viewModelScope)
    }

    fun getTopArticles() {
        flowOf(Unit)
            .debounce(1500)
            .flatMapConcat { useCase.getTopArticles() }
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