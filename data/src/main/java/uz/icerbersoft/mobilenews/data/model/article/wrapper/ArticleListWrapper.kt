package uz.icerbersoft.mobilenews.data.model.article.wrapper

import uz.icerbersoft.mobilenews.data.model.article.Article

data class ArticleListWrapper(val articles: List<Article>, val isFromOfflineSource: Boolean)
