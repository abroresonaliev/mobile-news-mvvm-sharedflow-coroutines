package uz.icerbersoft.mobilenews.app.presentation.common.model

import uz.icerbersoft.mobilenews.data.model.article.Article

sealed class ArticleWrapper {

    object LoadingItem : ArticleWrapper()

    data class ArticleItem(val article: Article) : ArticleWrapper()

    object EmptyItem : ArticleWrapper()

    object ErrorItem : ArticleWrapper()
}
