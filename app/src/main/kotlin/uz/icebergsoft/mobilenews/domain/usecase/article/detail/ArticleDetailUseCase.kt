package uz.icebergsoft.mobilenews.domain.usecase.article.detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingEvent

interface ArticleDetailUseCase {

    val articleDetailSharedFlow: SharedFlow<LoadingEvent<Article>>

    fun getArticle(articleId: String): Flow<Unit>

    fun updateBookmark(article: Article): Flow<Unit>
}