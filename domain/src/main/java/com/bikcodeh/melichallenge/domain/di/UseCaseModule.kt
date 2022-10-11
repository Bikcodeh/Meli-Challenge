package com.bikcodeh.melichallenge.domain.di

import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import com.bikcodeh.melichallenge.domain.usecase.SearchProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun providesSearchProductsUC(meliRepository: MeliRepository): SearchProductsUseCase =
        SearchProductsUseCase(meliRepository)
}