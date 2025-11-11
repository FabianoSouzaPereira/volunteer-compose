package com.fabianospdev.volunteerscompose.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.home.domain.entities.HomeEntity
import com.fabianospdev.volunteerscompose.features.home.domain.usecases.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private fun getHomeData() {
        if (!retryController.isRetryEnabled.value) {
            _showRetryLimitReached.value = true
            return
        }

        _state.value = HomeState.HomeLoading

        viewModelScope.launch {
            // Simulate data fetching
            try {
                // Replace with actual data fetching logic
                // val data = homeUseCase.getHomeData()
                // _state.value = HomeState.HomeSuccess(data)
                _state.value =
                    HomeState.HomeSuccess(HomeEntity("Sample Home Data", "Home", "Additional Info"))
            } catch (e: Exception) {
                _state.value = HomeState.HomeError(e.message ?: "Unknown Error")
            }

        }
    }

    fun resetState() {
        _state.value = HomeState.HomeIdle
    }

    fun onRetry() {
        resetState()
    }

    fun clearInputFields() {
        // TODO: Clear any input fields if necessary
    }

    fun resetAll() {
        clearInputFields()
        _showRetryLimitReached.value = false
        resetState()
    }
}