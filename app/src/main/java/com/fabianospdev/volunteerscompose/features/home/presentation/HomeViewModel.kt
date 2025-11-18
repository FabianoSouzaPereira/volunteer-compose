package com.fabianospdev.volunteerscompose.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.home.domain.entities.HomeEntity
import com.fabianospdev.volunteerscompose.features.home.domain.usecases.HomeUseCase
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeNavigationEvent
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val retryController: RetryController
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.HomeIdle)
    val state: StateFlow<HomeState> = _state

    private val _showRetryLimitReached = MutableStateFlow(false)
    val showRetryLimitReached: StateFlow<Boolean> get() = _showRetryLimitReached

    private val _navigationEvents = MutableSharedFlow<HomeNavigationEvent>()
    val navigationEvents: SharedFlow<HomeNavigationEvent> = _navigationEvents.asSharedFlow()

    init {
        observeRetryController()
        loadHomeData()
    }

    private fun observeRetryController() {
        viewModelScope.launch {
            retryController.isRetryLimitReached.collect { reached ->
                _showRetryLimitReached.value = reached
            }
        }
    }

    private fun loadHomeData() {
        if (!retryController.isRetryEnabled.value) {
            _showRetryLimitReached.value = true
            return
        }

        _state.value = HomeState.HomeLoading

        viewModelScope.launch {
            try {
                // Simulate data fetching - substitua pela lógica real
                // val data = homeUseCase.getHomeData()
                // _state.value = HomeState.HomeSuccess(data)

                // Simulação de delay para carregamento
                kotlinx.coroutines.delay(1000)

                val data = HomeEntity("Sample Home Data", "Home", "Additional Info")
                _state.value = HomeState.HomeSuccess(data)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        retryController.incrementRetryCount()
        val message = throwable.message ?: "Unknown Error"

        val errorState = when {
            message.contains("timeout", ignoreCase = true) ->
                HomeState.HomeTimeoutError(message)
            message.contains("network", ignoreCase = true) ->
                HomeState.HomeNoConnection(message)
            message.contains("unauthorized", ignoreCase = true) ->
                HomeState.HomeUnauthorized(message)
            else -> HomeState.HomeError(message)
        }

        _state.value = errorState
    }


    fun onNavigateToSettings() {
        viewModelScope.launch {
            _navigationEvents.emit(HomeNavigationEvent.NavigateToSettings)
        }
    }

    fun onNavigateToProfile() {
        viewModelScope.launch {
            _navigationEvents.emit(HomeNavigationEvent.NavigateToProfile)
        }
    }

    fun onNavigateToLogin() {
        viewModelScope.launch {
            _navigationEvents.emit(HomeNavigationEvent.NavigateToLogin)
        }
    }

    fun onNavigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(HomeNavigationEvent.NavigateBack)
        }
    }

    fun onRetry() {
        if (retryController.isRetryEnabled.value) {
            resetState()
            loadHomeData()
        }
    }

    fun onRefresh() {
        resetState()
        loadHomeData()
    }

    fun onItemClick(itemId: String) {
        viewModelScope.launch {
            // Exemplo: navegar para detalhes do item
            // _navigationEvents.emit(HomeNavigationEvent.NavigateToItemDetail(itemId))
        }
    }

    fun resetState() {
        _state.value = HomeState.HomeIdle
    }

    fun clearInputFields() {
        // Limpa campos de input se houver
    }

    fun resetAll() {
        clearInputFields()
        retryController.resetRetryLimitNotification()
        resetState()
    }
}