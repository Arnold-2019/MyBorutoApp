package com.example.myborutoapp.domain.model

import androidx.annotation.DrawableRes
import com.example.myborutoapp.R

sealed class OnBoardingPage(
    @DrawableRes val image: Int,
    val tile: String,
    val description: String
) {
    object First: OnBoardingPage(
        image = R.drawable.greetings,
        tile = "Greetings",
        description = "Are you a Boruto fan? Because if you are then we have a great news for you!"
    )
    object Second: OnBoardingPage(
        image = R.drawable.explore,
        tile = "Explore",
        description = "Find your favorite heroes and learn some of the things that you didn't know about.   "
    )
    object Third: OnBoardingPage(
        image = R.drawable.power,
        tile = "Power",
        description = "Check out your hero's power and  see how much are they strong comparing to others."
    )
}
