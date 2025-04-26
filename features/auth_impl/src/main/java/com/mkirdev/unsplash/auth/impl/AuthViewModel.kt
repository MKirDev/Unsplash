package com.mkirdev.unsplash.auth.impl

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel : ViewModel(), AuthContract {

    private val _uiState = MutableStateFlow<AuthContract.State>(
        AuthContract.State.Idle
    )
    private val _effect = MutableStateFlow<AuthContract.Effect?>(null)

    override val uiState: StateFlow<AuthContract.State> = _uiState.asStateFlow()
    override val effect: StateFlow<AuthContract.Effect?> = _effect.asStateFlow()

    override fun handleEvent(event: AuthContract.Event) {
        when (event) {
            AuthContract.Event.NotificationEvent -> onNotificationClose()
            AuthContract.Event.AuthEvent -> onAuth()
            is AuthContract.Event.TokenReceivedEventSuccess -> onTokenReceivedSuccess(event.token)
            is AuthContract.Event.TokenReceivedEventFailure -> onTokenReceivedFailure(event.error)
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onAuth() {
        _effect.update {
            AuthContract.Effect.Auth
        }
    }

    private fun onTokenReceivedSuccess(token: String) {
        // some code....
        _uiState.update {
            AuthContract.State.Success
        }
        // some code...
        _uiState.update {
            AuthContract.State.Idle
        }
        _effect.update {
            AuthContract.Effect.PostAuth
        }
    }

    private fun onTokenReceivedFailure(error: String) {
        // some code....
        _uiState.update {
            AuthContract.State.Error(error)
        }
    }

    private fun onNotificationClose() {
        _uiState.update {
            AuthContract.State.Idle
        }
    }

}