package uz.icebergsoft.mobilenews.presentation.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCase
import uz.icebergsoft.mobilenews.presentation.presentation.detail.router.ArticleDetailRouter
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingEvent
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingEvent.*
import javax.inject.Inject
import kotlin.properties.Delegates

class ArticleDetailViewModel @Inject constructor(
    private val useCase: ArticleDetailUseCase,
    private val router: ArticleDetailRouter
) : ViewModel() {

    private var currentArticleId: String by Delegates.notNull()

    fun setArticleId(value: String) {
        currentArticleId = value
    }

    private val _articleDetailLiveData = MutableLiveData<LoadingEvent<Article>>()
    val articleDetailLiveData: LiveData<LoadingEvent<Article>> = _articleDetailLiveData


    fun getArticleDetail() {
        useCase
            .getArticle(currentArticleId)
            .onStart { _articleDetailLiveData.value = LoadingState }
            .catch { _articleDetailLiveData.value = ErrorState(it.message) }
            .onEach { _articleDetailLiveData.value = SuccessState(it) }
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