package uz.icebergsoft.mobilenews.presentation.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import uz.icebergsoft.mobilenews.domain.data.model.article.Article
import uz.icebergsoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCase
import uz.icebergsoft.mobilenews.presentation.presentation.detail.router.ArticleDetailRouter
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingEvent
import javax.inject.Inject
import kotlin.properties.Delegates

class ArticleDetailViewModel @Inject constructor(
    private val router: ArticleDetailRouter,
    private val useCase: ArticleDetailUseCase
) : ViewModel() {

    val articleDetailSharedFlow: SharedFlow<LoadingEvent<Article>>
        get() = useCase.articleDetailSharedFlow

    private var currentArticleId: String by Delegates.notNull()

    fun setArticleId(value: String) {
        currentArticleId = value
    }

    fun getArticleDetail() {
        useCase
            .getArticle(currentArticleId)
            .launchIn(viewModelScope)
    }

    fun updateBookmark(article: Article) {
        viewModelScope.launch {
            useCase
                .updateBookmark(article)
                .collect { }
        }
    }

    fun back() = router.back()
}