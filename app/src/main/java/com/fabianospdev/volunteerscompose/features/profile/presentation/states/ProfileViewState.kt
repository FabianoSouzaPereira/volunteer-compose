package com.fabianospdev.volunteerscompose.features.profile.presentation.states

data class ProfileViewState(
    val screenState: ProfileState = ProfileState.ProfileLoading,
    val formState: ProfileFormState = ProfileFormState()
)
