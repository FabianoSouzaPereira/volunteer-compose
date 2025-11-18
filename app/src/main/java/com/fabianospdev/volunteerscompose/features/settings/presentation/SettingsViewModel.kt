package com.fabianospdev.volunteerscompose.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.features.settings.domain.entities.SettingsResponseEntity
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<SettingsState>(SettingsState.SettingsIdle)
    val state: StateFlow<SettingsState> = _state

    private val _navigationEvents = MutableSharedFlow<SettingsNavigationEvent>()
    val navigationEvents: SharedFlow<SettingsNavigationEvent> = _navigationEvents.asSharedFlow()

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> = _isDarkModeEnabled

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    init {
        loadSettings()
    }

    private fun loadSettings() {
        _state.value = SettingsState.SettingsLoading

        viewModelScope.launch {
            kotlinx.coroutines.delay(500)

            val settingsData = SettingsResponseEntity(
                darkMode = false,
                notifications = true,
                language = "Português",
                theme = "Default"
            )

            _isDarkModeEnabled.value = settingsData.darkMode
            _notificationsEnabled.value = settingsData.notifications
            _state.value = SettingsState.SettingsSuccess(settingsData)
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
        _isDarkModeEnabled.value = enabled
        // Aqui você salvaria a configuração
        saveSettings()
    }

    fun onNotificationsToggle(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        // Aqui você salvaria a configuração
        saveSettings()
    }

    private fun saveSettings() {
        viewModelScope.launch {
            // Simular salvamento
            kotlinx.coroutines.delay(300)
            // Aqui você salvaria as configurações no backend/local
        }
    }

    fun onLogout() {
        // Lógica de logout
        viewModelScope.launch {
            // Limpar dados do usuário, tokens, etc.
            _navigationEvents.emit(SettingsNavigationEvent.NavigateToHome)
        }
    }

    fun onRetry() {
        loadSettings()
    }

    fun resetState() {
        _state.value = SettingsState.SettingsIdle
    }
}