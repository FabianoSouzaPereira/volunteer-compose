package com.fabianospdev.baseapp.features.settings.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<SettingsState>(SettingsState.SettingsIdle)
    val state: StateFlow<SettingsState> = _state

    //TODO: Implement SettingsViewModel logic
}
