package uz.icebergsoft.mobilenews.presentation.application.di.data

import dagger.Binds
import dagger.Module
import uz.icebergsoft.mobilenews.data.repository.article.ArticleRepositoryImpl
import uz.icebergsoft.mobilenews.data.repository.settings.SettingsRepositoryImpl
import uz.icebergsoft.mobilenews.domain.data.repository.article.ArticleRepository
import uz.icebergsoft.mobilenews.domain.data.repository.settings.SettingsRepository

@Module
internal interface DataDaggerModuleRepository {

    @Binds
    fun articleRepository(
        impl: ArticleRepositoryImpl
    ): ArticleRepository

    @Binds
    fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository
}