package com.bikcodeh.melichallenge.data.di

import com.bikcodeh.melichallenge.data.repository.MeliRepositoryImpl
import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesMeliRepository(meliRepositoryImpl: MeliRepositoryImpl): MeliRepository
}