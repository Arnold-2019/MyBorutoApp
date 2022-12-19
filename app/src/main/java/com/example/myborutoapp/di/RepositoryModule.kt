package com.example.myborutoapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.example.myborutoapp.data.repository.DataStoreOperationsImpl
import com.example.myborutoapp.data.repository.RemoteDataSourceImpl
import com.example.myborutoapp.data.repository.Repository
import com.example.myborutoapp.domain.repository.DataStoreOperations
import com.example.myborutoapp.domain.repository.RemoteDataSource
import com.example.myborutoapp.domain.usecases.UseCases
import com.example.myborutoapp.domain.usecases.getallheroes.GetAllHeroes
import com.example.myborutoapp.domain.usecases.readonboarding.ReadOnBoardingUseCase
import com.example.myborutoapp.domain.usecases.saveonboarding.SaveOnBoardingUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@Module(includes = [RepositoryModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Binds
        fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations = DataStoreOperationsImpl(context = context)

    @Provides
    @Singleton
    fun provideUseCases(
        repository: Repository
    ): UseCases = UseCases(
        saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
        readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
        getAllHeroesUseCase = GetAllHeroes(repository)
    )
}
