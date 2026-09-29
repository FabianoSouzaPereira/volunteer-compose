package com.fabianospdev.volunteerscompose.features.stub.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.helpers.mutableNavigationEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StubViewModel @Inject constructor() : ViewModel() {
    private val _navigationEvents = mutableNavigationEvents<StubNavigationEvent>()
    val navigationEvents: SharedFlow<StubNavigationEvent> = _navigationEvents.asSharedFlow()

    fun onNavigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(StubNavigationEvent.NavigateBack)
        }
    }
}
