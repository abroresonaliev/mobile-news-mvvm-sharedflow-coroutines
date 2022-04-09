package uz.icerbersoft.mobilenews.domain.data.entity.article

import uz.icerbersoft.mobilenews.domain.data.entity.article.Article

data class ArticleListWrapper(val articles: List<Article>, val isFromOfflineSource: Boolean)
