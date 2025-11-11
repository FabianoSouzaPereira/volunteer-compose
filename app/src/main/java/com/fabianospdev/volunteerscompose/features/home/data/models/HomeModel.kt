package com.fabianospdev.volunteerscompose.features.home.data.models

import com.fabianospdev.volunteerscompose.features.home.domain.entities.HomeEntity

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