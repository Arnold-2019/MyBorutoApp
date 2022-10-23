package com.example.myborutoapp.di

import android.content.Context
import com.example.myborutoapp.data.repository.DataStoreOperationsImpl
import com.example.myborutoapp.data.repository.Repository
import com.example.myborutoapp.domain.repository.DataStoreOperations
import com.example.myborutoapp.domain.usecases.UseCases
import com.example.myborutoapp.domain.usecases.readonboarding.ReadOnBoardingUseCase
import com.example.myborutoapp.domain.usecases.saveonboarding.SaveOnBoardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCases(
        repository: Repository
    ): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
        )
    }
}
