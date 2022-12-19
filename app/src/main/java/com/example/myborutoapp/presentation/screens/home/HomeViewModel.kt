package com.example.myborutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.myborutoapp.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCases: UseCases
) : ViewModel() {

    val getAllHeroes = useCases.getAllHeroesUseCase
}
