package com.example.myborutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myborutoapp.data.local.BorutoDatabase
import com.example.myborutoapp.data.remote.BorutoApi
import com.example.myborutoapp.domain.model.ApiResponse
import com.example.myborutoapp.domain.model.Hero
import com.example.myborutoapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prePage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prePage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = borutoApi.getAllHeroes(page = page)
            addDataToLocal(response, loadType)
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { hero ->
                heroRemoteKeysDao.getRemoteKeys(id = hero.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { hero ->
                heroRemoteKeysDao.getRemoteKeys(id = hero.id)
            }
    }

    private suspend fun addDataToLocal(
        response: ApiResponse,
        loadType: LoadType
    ) {
        if (response.heroes.isNotEmpty()) {
            borutoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    heroDao.deleteAllHeroes()
                    heroRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.heroes.map { hero ->
                    HeroRemoteKeys(
                        id = hero.id,
                        prevPage = response.prevPage,
                        nextPage = response.nextPage
                    )
                }
                heroDao.addHeroes(heroes = response.heroes)
                heroRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
            }
        }
    }
}
