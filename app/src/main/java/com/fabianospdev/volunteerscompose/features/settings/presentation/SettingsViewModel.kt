package com.fabianospdev.volunteerscompose.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.TokenManager
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.BadRequestException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.NetworkException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.TimeoutException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.UnauthorizedException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.ValidationException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.errorMessage
import com.fabianospdev.volunteerscompose.core.helpers.mutableNavigationEvents
import com.fabianospdev.volunteerscompose.features.settings.domain.entities.SettingsResponseEntity
import com.fabianospdev.volunteerscompose.features.settings.domain.usecases.SettingsUseCase
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsNavigationEvent
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsState
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsViewState
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
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val tokenManager: TokenManager,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(SettingsViewState())
    val viewState: StateFlow<SettingsViewState> = _viewState.asStateFlow()

    private val _navigationEvents = mutableNavigationEvents<SettingsNavigationEvent>()
    val navigationEvents: SharedFlow<SettingsNavigationEvent> = _navigationEvents.asSharedFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        _viewState.update { it.copy(screenState = SettingsState.SettingsLoading) }

        viewModelScope.launch(dispatcherProvider.io) {
            settingsUseCase.getSettings().fold(
                onSuccess = { settings ->
                    _viewState.update {
                        it.copy(
                            screenState = SettingsState.SettingsSuccess(settings),
                            settings = settings
                        )
                    }
                },
                onFailure = { throwable ->
                    _viewState.update { it.copy(screenState = throwable.toSettingsErrorState()) }
                }
            )
        }
    }

    fun onNavigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(SettingsNavigationEvent.NavigateBack)
        }
    }

    fun onNavigateToHome() {
        viewModelScope.launch {
            _navigationEvents.emit(SettingsNavigationEvent.NavigateToHome)
        }
    }

    fun onNavigateToProfile() {
        viewModelScope.launch {
            _navigationEvents.emit(SettingsNavigationEvent.NavigateToProfile)
        }
    }

    fun onNavigateToAbout() {
        viewModelScope.launch {
            _navigationEvents.emit(SettingsNavigationEvent.NavigateToAbout)
        }
    }

    fun onDarkModeToggle(enabled: Boolean) {
        val updated = _viewState.value.settings.copy(darkMode = enabled)
        _viewState.update { it.copy(settings = updated) }
        saveSettings(updated)
    }

    fun onNotificationsToggle(enabled: Boolean) {
        val updated = _viewState.value.settings.copy(notifications = enabled)
        _viewState.update { it.copy(settings = updated) }
        saveSettings(updated)
    }

    private fun saveSettings(settings: SettingsResponseEntity) {
        viewModelScope.launch(dispatcherProvider.io) {
            settingsUseCase.saveSettings(settings)
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            tokenManager.clearToken()
            _navigationEvents.emit(SettingsNavigationEvent.NavigateToLogin)
        }
    }

    fun onRetry() {
        loadSettings()
    }

    fun resetState() {
        _viewState.update { it.copy(screenState = SettingsState.SettingsIdle) }
    }
}

private fun Throwable.toSettingsErrorState(): SettingsState {
    val message = errorMessage()
    return when (this) {
        is TimeoutException -> SettingsState.SettingsTimeoutError(message)
        is NetworkException -> SettingsState.SettingsNoConnection(message)
        is UnauthorizedException -> SettingsState.SettingsUnauthorized(message)
        is BadRequestException, is ValidationException -> SettingsState.SettingsValidationError(message)
        else -> SettingsState.SettingsError(message)
    }
}
