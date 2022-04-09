package uz.icerbersoft.mobilenews.presentation.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.presentation.presentation.detail.router.ArticleDetailRouter
import uz.icerbersoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCase
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

    private val _articleDetailLiveData = MutableLiveData<ArticleDetailLoadingState>()
    val articleDetailLiveData: LiveData<ArticleDetailLoadingState>
        get() = _articleDetailLiveData


    fun getArticleDetail() {
        useCase
            .getArticle(currentArticleId)
            .onStart { _articleDetailLiveData.value = ArticleDetailLoadingState.Loading }
            .catch { _articleDetailLiveData.value = ArticleDetailLoadingState.Failure(it) }
            .onEach { _articleDetailLiveData.value = ArticleDetailLoadingState.Success(it) }
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