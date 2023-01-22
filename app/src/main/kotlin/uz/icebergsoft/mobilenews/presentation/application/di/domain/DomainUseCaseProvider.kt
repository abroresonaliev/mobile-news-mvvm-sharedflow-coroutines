package uz.icebergsoft.mobilenews.presentation.application.di.domain

import uz.icebergsoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCase
import uz.icebergsoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCase
import uz.icebergsoft.mobilenews.domain.usecase.article.readlater.ReadLaterArticlesUseCase
import uz.icebergsoft.mobilenews.domain.usecase.article.recommended.RecommendedArticlesUseCase
import uz.icebergsoft.mobilenews.domain.usecase.bookmark.BookmarkUseCase
import uz.icebergsoft.mobilenews.domain.usecase.daynight.DayNightModeUseCase

interface DomainUseCaseProvider {
    val articleDetailUseCase: ArticleDetailUseCase
    val dashboardArticlesUseCase: DashboardArticlesUseCase
    val dayNightModeUseCase: DayNightModeUseCase
    val readLaterArticlesUseCase: ReadLaterArticlesUseCase
    val recommendedArticlesUseCase: RecommendedArticlesUseCase
    val bookmarkUseCase: BookmarkUseCase
}