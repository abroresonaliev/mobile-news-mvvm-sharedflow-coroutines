package uz.icerbersoft.mobilenews.app.presentation.detail

import uz.icerbersoft.mobilenews.data.model.article.Article

sealed class ArticleDetailResource {

    object Loading : ArticleDetailResource()

    data class Success(val article : Article) : ArticleDetailResource()

    data class Failure(val throwable: Throwable) : ArticleDetailResource()
}