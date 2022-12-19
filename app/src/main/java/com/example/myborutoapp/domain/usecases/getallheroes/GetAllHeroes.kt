package com.example.myborutoapp.domain.usecases.getallheroes

import androidx.paging.PagingData
import com.example.myborutoapp.data.repository.Repository
import com.example.myborutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class GetAllHeroes(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Hero>> {
        return repository.getAllHeroes()
    }
}
