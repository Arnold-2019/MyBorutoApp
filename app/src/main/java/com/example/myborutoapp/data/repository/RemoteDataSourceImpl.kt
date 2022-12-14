package com.example.myborutoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myborutoapp.data.local.BorutoDatabase
import com.example.myborutoapp.data.paging_source.HeroRemoteMediator
import com.example.myborutoapp.data.remote.BorutoApi
import com.example.myborutoapp.domain.model.Hero
import com.example.myborutoapp.domain.repository.RemoteDataSource
import com.example.myborutoapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class RemoteDataSourceImpl @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase,
    private val remoteMediator: HeroRemoteMediator
) : RemoteDataSource {

    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { borutoDatabase.heroDao().getAllHeroes() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(): Flow<PagingData<Hero>> {
        TODO("Not yet implemented")
    }
}
