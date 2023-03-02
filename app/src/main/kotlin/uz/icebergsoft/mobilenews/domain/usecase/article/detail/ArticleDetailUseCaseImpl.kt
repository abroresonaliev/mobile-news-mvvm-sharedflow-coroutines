package uz.icebergsoft.mobilenews.domain.usecase.article.detail

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.icebergsoft.mobilenews.domain.data.model.article.Article
import uz.icebergsoft.mobilenews.domain.data.repository.article.ArticleRepository
import uz.icebergsoft.mobilenews.domain.usecase.bookmark.BookmarkUseCase
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingEvent
import javax.inject.Inject

class ArticleDetailUseCaseImpl @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val bookmarkUseCase: BookmarkUseCase
) : ArticleDetailUseCase {

    private val _articleDetailSharedFlow = MutableSharedFlow<LoadingEvent<Article>>()
    override val articleDetailSharedFlow: SharedFlow<LoadingEvent<Article>>
        get() = _articleDetailSharedFlow.asSharedFlow()

    override fun getArticle(articleId: String): Flow<Unit> {
        return articleRepository.getArticle(articleId)
            .onStart { _articleDetailSharedFlow.emit(LoadingEvent.LoadingState) }
            .onEach { _articleDetailSharedFlow.emit(LoadingEvent.SuccessState(it)) }
            .catch { _articleDetailSharedFlow.emit(LoadingEvent.ErrorState(it.message)) }
            .map { Unit }
            .flowOn(Dispatchers.IO)
    }

    override fun updateBookmark(article: Article): Flow<Unit> {
        return bookmarkUseCase.updateBookmark(article)
    }
}