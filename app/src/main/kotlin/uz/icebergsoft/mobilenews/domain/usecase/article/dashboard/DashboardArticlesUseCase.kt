package uz.icebergsoft.mobilenews.domain.usecase.article.dashboard

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.icebergsoft.mobilenews.domain.data.model.article.Article
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent

interface DashboardArticlesUseCase {

    val breakingArticlesSharedFlow: SharedFlow<LoadingListEvent<Article>>

    val topArticlesSharedFlow: SharedFlow<LoadingListEvent<Article>>

    fun getBreakingArticles(): Flow<Unit>

    fun getTopArticles(): Flow<Unit>

    fun updateBookmark(article: Article): Flow<Unit>
}