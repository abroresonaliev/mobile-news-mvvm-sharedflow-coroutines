package uz.icebergsoft.mobilenews.domain.usecase.article.dashboard

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.domain.data.repository.article.ArticleRepository
import uz.icebergsoft.mobilenews.domain.usecase.bookmark.BookmarkUseCase
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingListEvent
import javax.inject.Inject

class DashboardArticlesUseCaseImpl @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val bookmarkUseCase: BookmarkUseCase
) : DashboardArticlesUseCase {

    private val _breakingArticlesSharedFlow = MutableSharedFlow<LoadingListEvent<Article>>(1)
    override val breakingArticlesSharedFlow: SharedFlow<LoadingListEvent<Article>>
        get() = _breakingArticlesSharedFlow.asSharedFlow()

    private val _topArticlesSharedFlow = MutableSharedFlow<LoadingListEvent<Article>>(1)
    override val topArticlesSharedFlow: SharedFlow<LoadingListEvent<Article>>
        get() = _topArticlesSharedFlow.asSharedFlow()

    override fun getBreakingArticles(): Flow<Unit> {
        return articleRepository.getBreakingNewsArticles()
            .onStart { _breakingArticlesSharedFlow.emit(LoadingListEvent.LoadingState) }
            .onEach {
                _breakingArticlesSharedFlow.emit(
                    if (it.articles.isEmpty()) LoadingListEvent.EmptyState
                    else LoadingListEvent.SuccessState(it.articles)
                )
            }
            .catch { _breakingArticlesSharedFlow.emit(LoadingListEvent.ErrorState(it.message)) }
            .map { Unit }
            .flowOn(Dispatchers.IO)
    }

    override fun getTopArticles(): Flow<Unit> {
        return articleRepository.getTopArticles()
            .onStart { _topArticlesSharedFlow.emit(LoadingListEvent.LoadingState) }
            .onEach {
                _topArticlesSharedFlow.emit(
                    if (it.articles.isEmpty()) LoadingListEvent.EmptyState
                    else LoadingListEvent.SuccessState(it.articles)
                )
            }
            .catch { _topArticlesSharedFlow.emit(LoadingListEvent.ErrorState(it.message)) }
            .map { Unit }
            .flowOn(Dispatchers.IO)
    }

    override fun updateBookmark(article: Article): Flow<Unit> {
        return bookmarkUseCase.updateBookmark(article)
    }
}