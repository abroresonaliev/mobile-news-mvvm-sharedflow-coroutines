package uz.icebergsoft.mobilenews.data.mapper

import uz.icebergsoft.mobilenews.data.utils.date.toFormattedDate
import uz.icebergsoft.mobilenews.domain.data.model.article.Article
import uz.icebergsoft.mobilenews.data.model.article.ArticleEntity
import uz.icebergsoft.mobilenews.domain.data.model.source.Source
import uz.icebergsoft.mobilenews.data.model.article.ArticleResponse
import uz.icebergsoft.mobilenews.data.model.source.SourceResponse

internal fun ArticleEntity.entityToArticle(): Article =
    Article(
        articleId = articleId,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = Source(sourceId, source),
        title = title,
        url = url,
        imageUrl = imageUrl,
        isBookmarked = isBookmarked
    )

internal fun ArticleResponse.responseToEntity(): ArticleEntity =
    ArticleEntity(
        articleId = url.hashCode().toString(),
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt?.toFormattedDate( ) ?: "",
        source = source.name,
        sourceId = source.id,
        title = title,
        url = url,
        imageUrl = imageUrl ?: "",
        isBookmarked = false
    )

internal fun SourceResponse.map(): Source =
    Source(id = id, name = name)