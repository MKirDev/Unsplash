package com.mkirdev.unsplash.onboarding.impl

import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import kotlinx.collections.immutable.PersistentList

interface OnboardingContract :
    UniFlowViewModel<OnboardingContract.Event, OnboardingContract.State, OnboardingContract.Effect?> {

    sealed interface State {
        data class Onboarding(
            val pages: PersistentList<OnboardingPage>,
            val isError: Boolean
        ) : State

        data object Idle : State
    }

    sealed interface Event {
        data object AuthOpenedEvent : Event

        data object FieldClosedEvent : Event
    }

    sealed interface Effect {
        data object Auth : Effect
    }
}