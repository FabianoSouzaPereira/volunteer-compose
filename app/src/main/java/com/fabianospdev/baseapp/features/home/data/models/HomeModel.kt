package com.fabianospdev.baseapp.features.home.data.models

import com.fabianospdev.baseapp.features.home.domain.entities.HomeEntity

data class HomeModel(
    val welcomeMessage: String,
    val title: String,
    val description: String
)

fun HomeModel.toEntity(): HomeEntity {
    return HomeEntity(
        welcomeMessage = welcomeMessage,
        title = title,
        description = description
    )
}