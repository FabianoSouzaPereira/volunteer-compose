package com.fabianospdev.volunteerscompose.features.about.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.helpers.mutableNavigationEvents
import com.fabianospdev.volunteerscompose.features.about.presentation.states.AboutNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor() : ViewModel() {
    private val _navigationEvents = mutableNavigationEvents<AboutNavigationEvent>()
    val navigationEvents: SharedFlow<AboutNavigationEvent> = _navigationEvents.asSharedFlow()

    fun onNavigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(AboutNavigationEvent.NavigateBack)
        }
    }
}
