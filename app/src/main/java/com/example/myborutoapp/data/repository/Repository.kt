package com.example.myborutoapp.data.repository

import androidx.paging.PagingData
import com.example.myborutoapp.domain.model.Hero
import com.example.myborutoapp.domain.repository.DataStoreOperations
import com.example.myborutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataStore: DataStoreOperations,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun saveOnBoardingState(isCompleted: Boolean) {
        dataStore.saveOnBoardingState(isCompleted = isCompleted)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

    fun getAllHeroes(): Flow<PagingData<Hero>> {
        return remoteDataSource.getAllHeroes()
    }
}
