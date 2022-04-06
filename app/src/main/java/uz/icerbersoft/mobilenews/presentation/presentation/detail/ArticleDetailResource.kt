package uz.icerbersoft.mobilenews.presentation.presentation.detail

import uz.icerbersoft.mobilenews.domain.data.entity.article.Article

sealed class ArticleDetailResource {

    object Loading : ArticleDetailResource()

    data class Success(val article : Article) : ArticleDetailResource()

    data class Failure(val throwable: Throwable) : ArticleDetailResource()
}