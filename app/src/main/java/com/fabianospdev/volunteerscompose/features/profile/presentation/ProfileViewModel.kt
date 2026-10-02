package com.fabianospdev.volunteerscompose.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.BadRequestException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.NetworkException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.TimeoutException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.UnauthorizedException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.ValidationException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.errorMessage
import com.fabianospdev.volunteerscompose.core.helpers.mutableNavigationEvents
import com.fabianospdev.volunteerscompose.features.profile.domain.entities.ProfileResponseEntity
import com.fabianospdev.volunteerscompose.features.profile.domain.usecases.ProfileUseCase
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileFormState
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileNavigationEvent
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileState
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(ProfileViewState())
    val viewState: StateFlow<ProfileViewState> = _viewState.asStateFlow()

    private val _navigationEvents = mutableNavigationEvents<ProfileNavigationEvent>()
    val navigationEvents: SharedFlow<ProfileNavigationEvent> = _navigationEvents.asSharedFlow()

    init {
        loadProfile()
    }

    fun onNameChange(name: String) {
        _viewState.update { state ->
            state.copy(formState = state.formState.copy(name = name, nameError = null))
        }
    }

    fun onPhoneChange(phone: String) {
        _viewState.update { state ->
            state.copy(formState = state.formState.copy(phone = phone))
        }
    }

    fun onSave() {
        val form = _viewState.value.formState
        val nameError = form.nameErrorMessage()
        if (nameError != null) {
            _viewState.update { it.copy(formState = form.copy(nameError = nameError)) }
            return
        }
        saveProfile(
            ProfileResponseEntity(
                name = form.name.trim(),
                email = form.email,
                phone = form.phone.trim()
            )
        )
    }

    fun onNavigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(ProfileNavigationEvent.NavigateBack)
        }
    }

    fun onRetry() {
        loadProfile()
    }

    private fun loadProfile() {
        _viewState.update { it.copy(screenState = ProfileState.ProfileLoading) }

        viewModelScope.launch(dispatcherProvider.io) {
            profileUseCase.getProfile().fold(
                onSuccess = { profile ->
                    _viewState.update {
                        it.copy(
                            screenState = ProfileState.ProfileSuccess(profile),
                            formState = ProfileFormState(
                                name = profile.name,
                                email = profile.email,
                                phone = profile.phone
                            )
                        )
                    }
                },
                onFailure = { throwable ->
                    _viewState.update { it.copy(screenState = throwable.toProfileErrorState()) }
                }
            )
        }
    }

    private fun saveProfile(profile: ProfileResponseEntity) {
        _viewState.update { it.copy(screenState = ProfileState.ProfileLoading) }

        viewModelScope.launch(dispatcherProvider.io) {
            profileUseCase.saveProfile(profile).fold(
                onSuccess = {
                    _viewState.update {
                        it.copy(
                            screenState = ProfileState.ProfileSuccess(profile),
                            formState = ProfileFormState(
                                name = profile.name,
                                email = profile.email,
                                phone = profile.phone
                            )
                        )
                    }
                },
                onFailure = { throwable ->
                    _viewState.update { it.copy(screenState = throwable.toProfileErrorState()) }
                }
            )
        }
    }
}

private fun Throwable.toProfileErrorState(): ProfileState {
    val message = errorMessage()
    return when (this) {
        is TimeoutException -> ProfileState.ProfileTimeoutError(message)
        is NetworkException -> ProfileState.ProfileNoConnection(message)
        is UnauthorizedException -> ProfileState.ProfileUnauthorized(message)
        is BadRequestException, is ValidationException -> ProfileState.ProfileValidationError(message)
        else -> ProfileState.ProfileError(message)
    }
}
