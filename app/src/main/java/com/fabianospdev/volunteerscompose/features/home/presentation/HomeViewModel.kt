package com.fabianospdev.volunteerscompose.features.home.presentation

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
import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.home.domain.usecases.HomeUseCase
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeNavigationEvent
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeState
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeViewState
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
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val retryController: RetryController,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState: StateFlow<HomeViewState> = _viewState.asStateFlow()

    private val _showRetryLimitReached = MutableStateFlow(false)
    val showRetryLimitReached: StateFlow<Boolean> get() = _showRetryLimitReached

    private val _navigationEvents = mutableNavigationEvents<HomeNavigationEvent>()
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

        _viewState.update { it.copy(screenState = HomeState.HomeLoading) }

        viewModelScope.launch(dispatcherProvider.io) {
            homeUseCase.getHomeData().fold(
                onSuccess = { data ->
                    retryController.resetRetryCount()
                    _viewState.update { it.copy(screenState = HomeState.HomeSuccess(data)) }
                },
                onFailure = { throwable ->
                    retryController.incrementRetryCount()
                    _viewState.update { it.copy(screenState = throwable.toHomeErrorState()) }
                }
            )
        }
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
            loadHomeData()
        }
    }

    fun onRefresh() {
        loadHomeData()
    }

    fun resetState() {
        _viewState.update { it.copy(screenState = HomeState.HomeIdle) }
    }

    fun resetAll() {
        retryController.resetRetryLimitNotification()
        resetState()
    }
}

private fun Throwable.toHomeErrorState(): HomeState {
    val message = errorMessage()
    return when (this) {
        is TimeoutException -> HomeState.HomeTimeoutError(message)
        is NetworkException -> HomeState.HomeNoConnection(message)
        is UnauthorizedException -> HomeState.HomeUnauthorized(message)
        is BadRequestException, is ValidationException -> HomeState.HomeValidationError(message)
        else -> HomeState.HomeError(message)
    }
}
