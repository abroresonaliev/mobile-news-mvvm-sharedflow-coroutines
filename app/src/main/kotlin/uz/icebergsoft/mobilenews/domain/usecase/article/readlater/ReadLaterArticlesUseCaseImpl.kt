package uz.icebergsoft.mobilenews.domain.usecase.article.readlater

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.domain.data.entity.article.ArticleListWrapper
import uz.icebergsoft.mobilenews.domain.data.repository.article.ArticleRepository
import uz.icebergsoft.mobilenews.domain.usecase.bookmark.BookmarkUseCase
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import javax.inject.Inject

class ReadLaterArticlesUseCaseImpl @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val bookmarkUseCase: BookmarkUseCase
) : ReadLaterArticlesUseCase {

    private val _articlesSharedFlow = MutableSharedFlow<LoadingListEvent<Article>>(1)
    override val articlesSharedFlow: SharedFlow<LoadingListEvent<Article>>
        get() = _articlesSharedFlow.asSharedFlow()

    override fun getReadLaterArticles(): Flow<Unit> {
        return articleRepository.getReadLaterArticles()
            .onStart { _articlesSharedFlow.emit(LoadingListEvent.LoadingState) }
            .onEach {
                _articlesSharedFlow.emit(
                    if (it.articles.isEmpty()) LoadingListEvent.EmptyState
                    else LoadingListEvent.SuccessState(it.articles)
                )
            }
            .catch { _articlesSharedFlow.emit(LoadingListEvent.ErrorState(it.message)) }
            .map { Unit }
            .flowOn(Dispatchers.IO)
    }

    override fun updateBookmark(article: Article): Flow<Unit> {
        return bookmarkUseCase.updateBookmark(article)
    }
}