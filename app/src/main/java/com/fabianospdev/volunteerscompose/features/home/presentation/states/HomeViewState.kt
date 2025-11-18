package com.fabianospdev.volunteerscompose.features.home.presentation.states

data class HomeViewState(
    val screenState: HomeState = HomeState.HomeIdle,
    val userData: String = "",
    val items: List<String> = emptyList()
)