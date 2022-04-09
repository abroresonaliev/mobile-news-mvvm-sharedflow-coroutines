package uz.icerbersoft.mobilenews.presentation.application.di.domain

import uz.icerbersoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCase
import uz.icerbersoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCase
import uz.icerbersoft.mobilenews.domain.usecase.article.readlater.ReadLaterArticlesUseCase
import uz.icerbersoft.mobilenews.domain.usecase.article.recommended.RecommendedArticlesUseCase
import uz.icerbersoft.mobilenews.domain.usecase.bookmark.BookmarkUseCase

interface DomainUseCaseProvider {
    val articleDetailUseCase: ArticleDetailUseCase
    val dashboardArticlesUseCase: DashboardArticlesUseCase
    val readLaterArticlesUseCase: ReadLaterArticlesUseCase
    val recommendedArticlesUseCase: RecommendedArticlesUseCase
    val bookmarkUseCase: BookmarkUseCase
}