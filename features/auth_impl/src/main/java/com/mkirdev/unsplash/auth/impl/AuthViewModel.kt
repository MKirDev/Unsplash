package com.mkirdev.unsplash.auth.impl

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mkirdev.unsplash.domain.usecases.auth.GetAuthRequestUseCase
import com.mkirdev.unsplash.domain.usecases.auth.GetSavedTokenUseCase
import com.mkirdev.unsplash.domain.usecases.auth.PerformTokensRequestUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.DeleteScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.user.AddCurrentUserUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
internal class AuthViewModel(
    private val getAuthRequestUseCase: GetAuthRequestUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val performTokensRequestUseCase: PerformTokensRequestUseCase,
    private val addCurrentUserUseCase: AddCurrentUserUseCase,
    private val deleteScheduleFlagUseCase: DeleteScheduleFlagUseCase
) : ViewModel(), AuthContract {

    private val _uiState = MutableStateFlow<AuthContract.State>(
        AuthContract.State.Idle
    )
    private val _effect = MutableStateFlow<AuthContract.Effect?>(null)

    @Stable
    override val uiState: StateFlow<AuthContract.State> = _uiState.asStateFlow()
    @Stable
    override val effect: StateFlow<AuthContract.Effect?> = _effect.asStateFlow()

    override fun handleEvent(event: AuthContract.Event) {
        when (event) {
            AuthContract.Event.NotificationReceivedEvent -> onNotificationClose()
            AuthContract.Event.AuthRequestedEvent -> onAuth()
            is AuthContract.Event.CodeReceivedSuccessEvent -> onCodeReceivedSuccess(event.code)
            is AuthContract.Event.CodeReceivedFailureEvent -> onCodeReceivedFailure(event.error)
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onAuth() {
        viewModelScope.launch {
            try {
                val authRequest = getAuthRequestUseCase.execute()
                _effect.update {
                    AuthContract.Effect.Auth(authRequest = authRequest)
                }
            } catch (t: Throwable) {
                _uiState.update {
                    AuthContract.State.Error(t.message.toString())
                }
            }
        }
    }

    private fun onCodeReceivedSuccess(code: String) {
        viewModelScope.launch {
            try {
                async {
                    performTokensRequestUseCase.execute(code)
                    addCurrentUserUseCase.execute()
                    deleteScheduleFlagUseCase.execute()
                }.await()
                launch {
                    val token = getSavedTokenUseCase.execute()
                    if (token.isNotEmpty()) {
                        _uiState.update {
                            AuthContract.State.Success
                        }
                        _effect.update {
                            AuthContract.Effect.PostAuth
                        }
                    }
                }
            } catch (t: Throwable) {
                AuthContract.State.Error(t.message.toString())
            }
        }
    }

    private fun onCodeReceivedFailure(error: String) {
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

internal class AuthViewModelFactory(
    private val getAuthRequestUseCase: GetAuthRequestUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val performTokensRequestUseCase: PerformTokensRequestUseCase,
    private val addCurrentUserUseCase: AddCurrentUserUseCase,
    private val deleteScheduleFlagUseCase: DeleteScheduleFlagUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return AuthViewModel(
            getAuthRequestUseCase = getAuthRequestUseCase,
            getSavedTokenUseCase = getSavedTokenUseCase,
            performTokensRequestUseCase = performTokensRequestUseCase,
            addCurrentUserUseCase = addCurrentUserUseCase,
            deleteScheduleFlagUseCase = deleteScheduleFlagUseCase
        ) as T
    }
}