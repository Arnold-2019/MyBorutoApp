package com.example.myborutoapp.domain.usecases

import com.example.myborutoapp.domain.usecases.readonboarding.ReadOnBoardingUseCase
import com.example.myborutoapp.domain.usecases.saveonboarding.SaveOnBoardingUseCase

data class UseCases(
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val saveOnBoardingUseCase: SaveOnBoardingUseCase
)
