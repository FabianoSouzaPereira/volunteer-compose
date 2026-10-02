package com.fabianospdev.volunteerscompose.features.profile.data.models

import com.fabianospdev.volunteerscompose.features.profile.domain.entities.ProfileResponseEntity

data class ProfileModel(
    val name: String,
    val email: String,
    val phone: String
)

fun ProfileModel.toEntity(): ProfileResponseEntity {
    return ProfileResponseEntity(
        name = name,
        email = email,
        phone = phone
    )
}

fun ProfileResponseEntity.toModel(): ProfileModel {
    return ProfileModel(
        name = name,
        email = email,
        phone = phone
    )
}
