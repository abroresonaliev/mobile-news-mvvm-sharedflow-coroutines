package uz.icebergsoft.mobilenews.data.datasource.rest.service

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import uz.icebergsoft.mobilenews.data.model.article.ArticleListResponse

internal interface ArticleRestService {
    
    @GET("${API_TOP_HEADINGS}${API_API_KEY}")
    fun getBreakingArticles(
        @Query(FIELD_SORT) sortBy: String = "popularity"
    ): Flow<ArticleListResponse>

    @GET("${API_TOP_HEADINGS}${API_API_KEY}")
    fun getTopArticles(
        @Query(FIELD_COUNTRY) country: String = "us",
        @Query(FIELD_CATEGORY) category: String = "business",
        @Query(FIELD_SORT) sortBy: String = "popularity"
    ): Flow<ArticleListResponse>

    @GET("${API_TOP_HEADINGS}${API_API_KEY}")
    fun getRecommendationArticles(
        @Query(FIELD_CATEGORY) category: String = "science",
        @Query(FIELD_SORT) sortBy: String = "popularity"
    ): Flow<ArticleListResponse>

    private companion object {
        const val API_TOP_HEADINGS: String = "top-headlines"

        const val API_API_KEY: String = "?country=us&apiKey=8eca259240354cd1b70a02b5f7185c62"

        const val FIELD_COUNTRY: String = "country"
        const val FIELD_CATEGORY: String = "category"
        const val FIELD_SORT: String = "sortBy"
    }
}