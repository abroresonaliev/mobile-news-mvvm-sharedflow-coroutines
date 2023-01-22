package uz.icebergsoft.mobilenews.domain.usecase.article.readlater

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.domain.data.entity.article.ArticleListWrapper
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent

interface ReadLaterArticlesUseCase {

    val articlesSharedFlow: SharedFlow<LoadingListEvent<Article>>

    fun getReadLaterArticles(): Flow<Unit>

    fun updateBookmark(article: Article): Flow<Unit>
}