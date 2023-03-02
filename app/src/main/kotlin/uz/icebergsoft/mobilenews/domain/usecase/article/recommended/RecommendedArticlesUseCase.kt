package uz.icebergsoft.mobilenews.domain.usecase.article.recommended

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.icebergsoft.mobilenews.domain.data.model.article.Article
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent

interface RecommendedArticlesUseCase {

    val articlesSharedFlow: SharedFlow<LoadingListEvent<Article>>

    fun getRecommendedArticles(): Flow<Unit>

    fun updateBookmark(article: Article): Flow<Unit>
}