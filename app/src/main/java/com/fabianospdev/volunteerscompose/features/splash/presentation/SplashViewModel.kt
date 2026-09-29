package com.fabianospdev.volunteerscompose.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.mutableNavigationEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _navigationEvents = mutableNavigationEvents<SplashNavigationEvent>()
    val navigationEvents: SharedFlow<SplashNavigationEvent> = _navigationEvents.asSharedFlow()

    init {
        viewModelScope.launch(dispatcherProvider.main) {
            delay(SPLASH_DELAY_MS)
            _navigationEvents.emit(SplashNavigationEvent.NavigateToLogin)
        }
    }

    private companion object {
        const val SPLASH_DELAY_MS = 4000L
    }
}
