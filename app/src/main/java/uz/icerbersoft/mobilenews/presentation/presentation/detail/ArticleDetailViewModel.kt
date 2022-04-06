package uz.icerbersoft.mobilenews.presentation.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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

    private val _articleDetailLiveData = MutableLiveData<ArticleDetailResource>()
    val articleDetailLiveData: LiveData<ArticleDetailResource>
        get() = _articleDetailLiveData


    fun getArticleDetail() {
        useCase
            .getArticle(currentArticleId)
            .onStart { _articleDetailLiveData.value = ArticleDetailResource.Loading }
            .catch { _articleDetailLiveData.value = ArticleDetailResource.Failure(it) }
            .onEach { _articleDetailLiveData.value = ArticleDetailResource.Success(it) }
            .launchIn(viewModelScope)
    }

    fun back() = router.back()
}