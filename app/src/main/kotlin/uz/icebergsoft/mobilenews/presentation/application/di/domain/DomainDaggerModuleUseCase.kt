package uz.icebergsoft.mobilenews.presentation.application.di.domain

import dagger.Binds
import dagger.Module
import dagger.Provides
import uz.icebergsoft.mobilenews.domain.data.repository.article.ArticleRepository
import uz.icebergsoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCase
import uz.icebergsoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCaseImpl
import uz.icebergsoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCase
import uz.icebergsoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCaseImpl
import uz.icebergsoft.mobilenews.domain.usecase.article.readlater.ReadLaterArticlesUseCase
import uz.icebergsoft.mobilenews.domain.usecase.article.readlater.ReadLaterArticlesUseCaseImpl
import uz.icebergsoft.mobilenews.domain.usecase.article.recommended.RecommendedArticlesUseCase
import uz.icebergsoft.mobilenews.domain.usecase.article.recommended.RecommendedArticlesUseCaseImpl
import uz.icebergsoft.mobilenews.domain.usecase.bookmark.BookmarkUseCase
import uz.icebergsoft.mobilenews.domain.usecase.bookmark.BookmarkUseCaseImpl
import uz.icebergsoft.mobilenews.domain.usecase.daynight.DayNightModeUseCase
import uz.icebergsoft.mobilenews.domain.usecase.daynight.DayNightModeUseCaseImpl
import uz.icebergsoft.mobilenews.domain.usecase.global.GlobalUseCase
import uz.icebergsoft.mobilenews.domain.usecase.global.GlobalUseCaseImpl
import uz.icebergsoft.mobilenews.domain.usecase.home.HomeUseCase
import uz.icebergsoft.mobilenews.domain.usecase.home.HomeUseCaseImpl
import javax.inject.Singleton

@Module(
    includes = [
        DomainDaggerModuleUseCase.Binders::class,
        DomainDaggerModuleUseCase.Providers::class
    ]
)
internal object DomainDaggerModuleUseCase {

    @Module
    interface Binders {

        @Binds
        fun bindArticleDetailUseCase(
            impl: ArticleDetailUseCaseImpl
        ): ArticleDetailUseCase

        @Binds
        fun bindDashboardArticlesUseCase(
            impl: DashboardArticlesUseCaseImpl
        ): DashboardArticlesUseCase

        @Binds
        fun bindDayNightUseCase(
            impl: DayNightModeUseCaseImpl
        ): DayNightModeUseCase

        @Binds
        fun bindReadLaterArticlesUseCase(
            impl: ReadLaterArticlesUseCaseImpl
        ): ReadLaterArticlesUseCase

        @Binds
        fun bindRecommendedArticlesUseCase(
            impl: RecommendedArticlesUseCaseImpl
        ): RecommendedArticlesUseCase
    }

    @Module
    object Providers {

        @JvmStatic
        @Provides
        @Singleton
        fun provideBookmarkUseCase(
            articleRepository: ArticleRepository
        ): BookmarkUseCase =
            BookmarkUseCaseImpl(articleRepository)

        @JvmStatic
        @Provides
        @Singleton
        fun provideGlobalUseCase(
        ): GlobalUseCase =
            GlobalUseCaseImpl()

        @JvmStatic
        @Provides
        @Singleton
        fun provideHomeUseCase(
        ): HomeUseCase =
            HomeUseCaseImpl()
    }
}