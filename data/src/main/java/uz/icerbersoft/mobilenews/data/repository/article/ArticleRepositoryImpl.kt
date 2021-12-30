package uz.icerbersoft.mobilenews.data.repository.article

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import uz.icerbersoft.mobilenews.data.datasource.database.dao.article.ArticleEntityDao
import uz.icerbersoft.mobilenews.data.datasource.rest.service.RestService
import uz.icerbersoft.mobilenews.data.mapper.entityToArticle
import uz.icerbersoft.mobilenews.data.mapper.responseToEntity
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.model.article.wrapper.ArticleListWrapper
import java.net.ConnectException
import java.net.UnknownHostException

internal class ArticleRepositoryImpl(
    private val articleEntityDao: ArticleEntityDao,
    private val restService: RestService
) : ArticleRepository {

    override fun getArticle(articleId: String): Flow<Article> {
        return articleEntityDao.getArticleEntityById(articleId)
            .map { it.entityToArticle() }
    }

    @FlowPreview
    override fun getArticles(): Flow<ArticleListWrapper> {
        return restService.getBreakingArticles()
            .onEach { it ->
                it.articles.forEach {
                    articleEntityDao.updateArticle(it.responseToEntity())
                }
            }
            .map { it -> it.articles.map { it.url } }
            .catch {
                if (it is ConnectException || it is UnknownHostException) emit(listOf())
                else throw it
            }
            .flatMapConcat { postUrls ->
                when {
                    postUrls.isNotEmpty() ->
                        articleEntityDao.getArticleEntitiesByUrl(postUrls.toTypedArray())
                    else -> articleEntityDao.getArticleEntities()
                }
                    .map { list -> list.map { it.entityToArticle() } }
                    .map { ArticleListWrapper(it, postUrls.isEmpty()) }
            }
    }

    @FlowPreview
    override fun getBreakingNewsArticles(): Flow<ArticleListWrapper> {
        return restService.getBreakingArticles()
            .onEach { it ->
                it.articles.forEach {
                    articleEntityDao.updateArticle(it.responseToEntity())
                }
            }
            .map { it -> it.articles.map { it.url } }
            .catch {
                if (it is ConnectException || it is UnknownHostException) emit(listOf())
                else throw it
            }
            .flatMapConcat { postUrls ->
                when {
                    postUrls.isNotEmpty() ->
                        articleEntityDao.getArticleEntitiesByUrl(postUrls.toTypedArray())
                    else -> articleEntityDao.getArticleEntities()
                }
                    .map { list -> list.map { it.entityToArticle() } }
                    .map { ArticleListWrapper(it, postUrls.isEmpty()) }
            }
    }

    @FlowPreview
    override fun getTopArticles(): Flow<ArticleListWrapper> {
        return restService.getTopArticles()
            .onEach { it ->
                it.articles.forEach {
                    articleEntityDao.updateArticle(it.responseToEntity())
                }
            }
            .map { it -> it.articles.map { it.url } }
            .catch {
                if (it is ConnectException || it is UnknownHostException) emit(listOf())
                else throw it
            }
            .flatMapConcat { postUrls ->
                when {
                    postUrls.isNotEmpty() ->
                        articleEntityDao.getArticleEntitiesByUrl(postUrls.toTypedArray())
                    else -> articleEntityDao.getArticleEntities()
                }
                    .map { list -> list.map { it.entityToArticle() } }
                    .map { ArticleListWrapper(it, postUrls.isEmpty()) }
            }
    }

    @FlowPreview
    override fun getRecommendationArticles(): Flow<ArticleListWrapper> {
        return restService.getRecommendationArticles()
            .onEach { it ->
                it.articles.forEach {
                    articleEntityDao.updateArticle(it.responseToEntity())
                }
            }
            .map { it -> it.articles.map { it.url } }
            .catch {
                if (it is ConnectException || it is UnknownHostException) emit(listOf())
                else throw it
            }
            .flatMapConcat { postUrls ->
                when {
                    postUrls.isNotEmpty() ->
                        articleEntityDao.getArticleEntitiesByUrl(postUrls.toTypedArray())
                    else -> articleEntityDao.getArticleEntities()
                }
                    .map { list -> list.map { it.entityToArticle() } }
                    .map { ArticleListWrapper(it, postUrls.isEmpty()) }
            }
    }

    @FlowPreview
    override fun getReadLaterArticles(): Flow<ArticleListWrapper> {
        return flow { emit(articleEntityDao.getArticleEntitiesByBookmark(true).first()) }
            .map { list -> list.map { it.entityToArticle() } }
            .map { ArticleListWrapper(it, true) }
    }

    override fun updateBookmark(articleId: String, isBookmarked: Boolean): Flow<Unit> {
        return flow { emit(articleEntityDao.updateBookmark(articleId, isBookmarked)) }
    }
}