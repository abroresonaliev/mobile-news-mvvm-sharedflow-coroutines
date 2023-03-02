package uz.icebergsoft.mobilenews.domain.usecase.bookmark

import kotlinx.coroutines.flow.Flow
import uz.icebergsoft.mobilenews.domain.data.model.article.Article

interface BookmarkUseCase {

    fun updateBookmark(article: Article): Flow<Unit>
}