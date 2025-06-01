package com.mkirdev.unsplash.auth.impl

import androidx.compose.runtime.Immutable
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel

internal interface AuthContract : UniFlowViewModel<AuthContract.Event, AuthContract.State, AuthContract.Effect?> {

    sealed interface State {
        data object Idle : State

        data object Success : State

        @Immutable
        data class Error(val message: String) : State
    }

    sealed interface Event {

        data object NotificationReceivedEvent : Event
        data object AuthRequestedEvent : Event
        data class CodeReceivedSuccessEvent(val code: String): Event

        data class CodeReceivedFailureEvent(val error: String) : Event

    }

    sealed interface Effect {
        data object BackPressed : Effect
        data class Auth(val authRequest: String) : Effect
        data object PostAuth : Effect

    }

}